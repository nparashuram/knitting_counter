package com.nparashuram.knittingcounter.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "counter_preferences")

class CounterPreferences(private val context: Context) {
    companion object {
        private val COUNTER_KEY = intPreferencesKey("counter_value")
    }

    val counterValue: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[COUNTER_KEY] ?: 0
        }

    suspend fun updateCounter(value: Int) {
        context.dataStore.edit { preferences ->
            preferences[COUNTER_KEY] = value
        }
    }
} 