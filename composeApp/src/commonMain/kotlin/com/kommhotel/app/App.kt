package com.kommhotel.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.kommhotel.app.features.auth.LoginScreen
import com.kommhotel.app.features.auth.RegisterScreen
import com.kommhotel.app.features.home.HomeScreen
import com.kommhotel.app.features.splash.SplashScreen
import com.kommhotel.app.navigation.Screen
import com.kommhotel.shared.di.initKoin
import kotlinx.coroutines.delay
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

// Represents the overall state of the application flow
private sealed class AppState {
    data object Loading : AppState()
    data object LoggedOut : AppState()
    data object Registering : AppState()
    data class LoggedIn(val sessionId: String) : AppState()
}

@Composable
fun App() {
    MaterialTheme {
        var appState by remember { mutableStateOf<AppState>(AppState.Loading) }

        // This effect simulates the initial loading and authentication check
        LaunchedEffect(Unit) {
            initKoin()
            delay(2000) // Show splash for 2 seconds
            appState = AppState.LoggedOut
        }

        when (val state = appState) {
            is AppState.Loading -> {
                SplashScreen()
            }
            is AppState.LoggedOut -> {
                LoginScreen(
                    onLoginSuccess = { sessionId ->
                        appState = AppState.LoggedIn(sessionId)
                    },
                    onRegisterClick = {
                        appState = AppState.Registering
                    }
                )
            }
            is AppState.Registering -> {
                RegisterScreen(
                    onRegisterSuccess = {
                        appState = AppState.LoggedOut
                    }
                )
            }
            is AppState.LoggedIn -> {
                MainAuthenticatedContent(state.sessionId)
            }
        }
    }
}

@Composable
private fun MainAuthenticatedContent(sessionId: String) {
    // For now, we directly show the HomeScreen.
    // In the future, this will host the main navigation graph (Bottom Nav, etc.)
    HomeScreen()
}
