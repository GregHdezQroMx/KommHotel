package com.kommhotel.shared.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create the DataStore instance as a Context extension property
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kommhotel_prefs")

/**
 * Android's actual implementation of the SessionManager using DataStore.
 */
actual class SessionManager(private val context: Context) {

    private companion object {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
    }

    actual suspend fun saveToken(token: String) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    actual fun getToken(): Flow<String?> {
        return context.dataStore.data.map { it[TOKEN_KEY] }
    }

    actual suspend fun clearToken() {
        context.dataStore.edit {
            it.remove(TOKEN_KEY)
        }
    }
}