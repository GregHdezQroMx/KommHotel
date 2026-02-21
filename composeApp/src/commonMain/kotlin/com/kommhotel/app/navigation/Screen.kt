package com.kommhotel.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * A sealed class representing the screens in the main (authenticated) part of the app.
 * We use NavKey from navigation-3 for type-safe navigation.
 */
sealed class Screen : NavKey {
    @Serializable
    data object Home : Screen()

    @Serializable
    data class RoomDetail(val roomId: String) : Screen()
}