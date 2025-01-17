package dk.dtu.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dk.dtu.weatherapp.domain.AlertRepository
import dk.dtu.weatherapp.models.Alert
import dk.dtu.weatherapp.ui.alerts.AlertsUIModel
import dk.dtu.weatherapp.ui.alerts.AlertsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FakeAlertRepository : AlertRepository() {
    private val _currentAlertFlow = MutableStateFlow<List<Alert>?>(null)
    override val currentAlertFlow: StateFlow<List<Alert>?> = _currentAlertFlow

    // Method to mock alerts
    fun mockAlerts(alerts: List<Alert>?) {
        _currentAlertFlow.value = alerts
    }

    override suspend fun getAlerts() {
        // Simulate fetching alerts
        _currentAlertFlow.value = mockAlertList
    }

    val mockAlertList = listOf(
        Alert(
            headline = "Severe Thunderstorm Warning",
            severity = "Severe",
            urgency = "Immediate",
            event = "Thunderstorm",
            description = "A severe thunderstorm is approaching the area."
        ),
        Alert(
            headline = "Flood Watch",
            severity = "Moderate",
            urgency = "Expected",
            event = "Flood",
            description = "Flooding is possible in low-lying areas."
        )
    )
}



@ExperimentalCoroutinesApi
class AlertsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeAlertRepository = FakeAlertRepository()
    private lateinit var viewModel: AlertsViewModel

    @Before
    fun setup() {
        viewModel = AlertsViewModel(alertRepository = fakeAlertRepository)
    }

    @Test
    fun `when repository emits alerts, ViewModel should emit Data state`() = runTest {
        // Arrange: Mock the alerts in the repository
        val mockAlerts = fakeAlertRepository.mockAlertList
        fakeAlertRepository.mockAlerts(mockAlerts)

        // Act: Trigger ViewModel to fetch alerts
        viewModel.getAlerts()

        // Assert: Verify the ViewModel state is updated with Data
        val state = viewModel.alertsUIState.value
        assertThat(state, `is`(AlertsUIModel.Data(mockAlerts)))
    }

    @Test
    fun `when repository emits null, ViewModel should emit RequestError state`() = runTest {
        // Arrange: Simulate no data from repository
        fakeAlertRepository.mockAlerts(null)

        // Act: Trigger ViewModel to fetch alerts
        viewModel.getAlerts()

        // Assert: Verify the ViewModel state is updated with RequestError
        val state = viewModel.alertsUIState.value
        assertThat(state, `is`(AlertsUIModel.RequestError))
    }

    @Test
    fun `when repository emits empty list, ViewModel should emit Empty state`() = runTest {
        // Arrange: Simulate empty data
        fakeAlertRepository.mockAlerts(emptyList())

        // Act: Trigger ViewModel to fetch alerts
        viewModel.getAlerts()

        // Assert: Verify the ViewModel state is updated with Empty
        val state = viewModel.alertsUIState.value
        assertThat(state, `is`(AlertsUIModel.Empty))
    }
}
