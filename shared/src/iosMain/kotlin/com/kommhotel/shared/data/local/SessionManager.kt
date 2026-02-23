package com.kommhotel.shared.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import platform.Foundation.NSUserDefaults

/**
 * The actual implementation of SessionManager for the iOS platform.
 * It uses NSUserDefaults for persistence.
 */
actual class SessionManager {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    private companion object {
        private const val TOKEN_KEY = "auth_token"
    }

    actual suspend fun saveToken(token: String) {
        userDefaults.setObject(token, forKey = TOKEN_KEY)
    }

    actual fun getToken(): Flow<String?> {
        return flowOf(userDefaults.stringForKey(TOKEN_KEY))
    }

    actual suspend fun clearToken() {
        userDefaults.removeObjectForKey(TOKEN_KEY)
    }
}