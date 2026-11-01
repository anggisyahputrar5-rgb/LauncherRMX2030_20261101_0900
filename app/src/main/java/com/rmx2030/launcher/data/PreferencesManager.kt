package com.rmx2030.launcher.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "launcher_prefs")

class PreferencesManager(private val context: Context) {
    companion object {
        val GRID_COLUMNS = intPreferencesKey("grid_columns")
        val THEME_NAME = stringPreferencesKey("theme_name")
    }

    val gridColumns: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[GRID_COLUMNS] ?: 4
    }

    val themeName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[THEME_NAME] ?: "MIDNIGHT"
    }

    suspend fun saveGridColumns(columns: Int) {
        context.dataStore.edit { prefs ->
            prefs[GRID_COLUMNS] = columns
        }
    }

    suspend fun saveThemeName(name: String) {
        context.dataStore.edit { prefs ->
            prefs[THEME_NAME] = name
        }
    }
}
