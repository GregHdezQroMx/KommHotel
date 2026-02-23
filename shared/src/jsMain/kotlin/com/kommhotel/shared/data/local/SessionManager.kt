package com.kommhotel.shared.data.local

import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.w3c.dom.set

/**
 * The actual implementation of SessionManager for the JavaScript platform.
 * It uses the browser's localStorage for persistence.
 */
actual class SessionManager {

    private companion object {
        private const val TOKEN_KEY = "auth_token"
    }

    actual suspend fun saveToken(token: String) {
        localStorage[TOKEN_KEY] = token
    }

    actual fun getToken(): Flow<String?> {
        // localStorage is synchronous, so we emit the current value as a single-item Flow.
        return flowOf(localStorage.getItem(TOKEN_KEY))
    }

    actual suspend fun clearToken() {
        localStorage.removeItem(TOKEN_KEY)
    }
}
