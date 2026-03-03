package com.kommhotel.shared.data.local

import kotlinx.browser.localStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.w3c.dom.set

actual class SessionManager {
    private companion object {
        private const val TOKEN_KEY = "auth_token"
    }

    actual suspend fun saveToken(token: String) {
        localStorage[TOKEN_KEY] = token
    }

    actual fun getToken(): Flow<String?> {
        return flowOf(localStorage.getItem(TOKEN_KEY))
    }

    actual suspend fun clearToken() {
        localStorage.removeItem(TOKEN_KEY)
    }
}
