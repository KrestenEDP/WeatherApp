package dk.dtu.weatherapp

import dk.dtu.weatherapp.domain.CurrentWeatherRepository
import dk.dtu.weatherapp.ui.homepage.HomepageViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain


// Fake Repository that returns predefined data
class FakeWeatherRepository : CurrentWeatherRepository() {
    // Here, you can simulate returning different data for testing
    override fun getWeatherData(): Flow<WeatherUIState> = flow {
        emit(WeatherUIState.Loading)  // Start with Loading
        kotlinx.coroutines.delay(500)   // Simulate a delay
        emit(WeatherUIState.Data("Sunny, 25°C"))  // Emit Data after a while
    }
}

@OptIn(ExperimentalCoroutinesApi::class) // Allow experimental coroutines API
class HomepageViewModelest {

    private lateinit var viewModel: HomepageViewModel
    private lateinit var fakeWeatherRepository: FakeWeatherRepository

    // Setting up the dispatcher for coroutines
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        // Set the main dispatcher for tests
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)

        // Initialize the fake repository
        fakeWeatherRepository = FakeWeatherRepository()

        // Create the ViewModel using the fake repository
        viewModel = HomepageViewModel(fakeWeatherRepository)
    }

    @Test
    fun `weatherUIState emits Loading followed by Data`() = runTest {
        // Collect the flow emitted by the ViewModel
        val weatherStateList = mutableListOf<WeatherUIState>()
        val job = launch {
            viewModel.weatherUIState.collect { weatherState ->
                weatherStateList.add(weatherState)
            }
        }

        // Let the flow emit items
        kotlinx.coroutines.delay(1000) // Wait for the flow to emit

        // Test that the weather state list contains "Loading" followed by "Data"
        assertEquals(listOf(WeatherUIState.Loading, WeatherUIState.Data("Sunny, 25°C")), weatherStateList)

        job.cancel()  // Cancel the collection
    }
}
