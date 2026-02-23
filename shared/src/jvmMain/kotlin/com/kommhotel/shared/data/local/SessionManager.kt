package com.kommhotel.shared.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * The actual implementation of SessionManager for the JVM platform.
 * This is a simple in-memory implementation for demonstration purposes.
 * The token will be lost when the application is closed.
 */
actual class SessionManager {

    private val inMemoryToken = MutableStateFlow<String?>(null)

    actual suspend fun saveToken(token: String) {
        inMemoryToken.value = token
    }

    actual fun getToken(): Flow<String?> {
        return inMemoryToken
    }

    actual suspend fun clearToken() {
        inMemoryToken.value = null
    }
}