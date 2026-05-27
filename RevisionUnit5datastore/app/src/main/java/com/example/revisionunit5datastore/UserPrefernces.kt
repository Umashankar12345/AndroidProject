package com.example.revisionunit5datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "Setting")

class UserPrefernces(private val context: Context) {

    companion object {

        val USER_NAME = stringPreferencesKey("user_name")

        val DARK_MODE = booleanPreferencesKey("Dark_mode")
    }

    // Save Username
    suspend fun savedUsername(name: String) {

        context.dataStore.edit { preferences ->

            preferences[USER_NAME] = name
        }
    }

    // Get Username
    val getUserName: Flow<String> =
        context.dataStore.data.map { preferences ->

            preferences[USER_NAME] ?: "No Name"
        }

    // Save Dark Mode
    suspend fun saveDarkMode(isDark: Boolean) {

        context.dataStore.edit { preferences ->

            preferences[DARK_MODE] = isDark
        }
    }

    // Get Dark Mode
    val getDarkMode: Flow<Boolean> =
        context.dataStore.data.map { preferences ->

            preferences[DARK_MODE] ?: false
        }
}