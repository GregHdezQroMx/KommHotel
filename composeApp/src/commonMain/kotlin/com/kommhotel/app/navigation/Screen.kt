package com.kommhotel.app.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

/**
 * A type-safe representation of the navigation destinations in the app.
 * Each object implements NavKey and is serializable.
 */
@Serializable
sealed class Screen : NavKey {
    @Serializable
    data object Splash : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object Home : Screen()
}