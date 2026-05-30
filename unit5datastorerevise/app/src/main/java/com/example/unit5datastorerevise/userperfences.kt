package com.example.unit5datastorerevise

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore("bank_Setings")
class userperfences(private val context: Context) {

    companion object {
        val BIOMETRIC_LOGIN = booleanPreferencesKey("biometric login")


    }
    suspend fun  saveBiometricStatus(enabled: Boolean){
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_LOGIN] = enabled
        }
    }
    val  biometricsStatus : Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[BIOMETRIC_LOGIN] ?: false
}
}