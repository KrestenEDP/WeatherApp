package dk.dtu.weatherapp.domain

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore


// DataStore Instance
private const val DATASTORE_NAME = "settings"
val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)


