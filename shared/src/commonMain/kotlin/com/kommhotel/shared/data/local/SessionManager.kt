package com.kommhotel.shared.data.local

import kotlinx.coroutines.flow.Flow

/**
 * A multiplatform abstraction for managing a user's session token.
 * This will be implemented on each platform using its native storage mechanism.
 */
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class SessionManager {

    /**
     * Saves the session token to persistent storage.
     */
    suspend fun saveToken(token: String)

    /**
     * Retrieves the session token as a Flow, allowing observers to react to changes.
     */
    fun getToken(): Flow<String?>

    /**
     * Clears the session token from storage (for logout).
     */
    suspend fun clearToken()
}