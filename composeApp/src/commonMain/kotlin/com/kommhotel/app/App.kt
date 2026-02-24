package com.kommhotel.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.kommhotel.app.di.initializeKoin
import com.kommhotel.app.features.auth.LoginScreen
import com.kommhotel.app.features.auth.RegisterScreen
import com.kommhotel.app.features.bookings.MyBookingsScreen
import com.kommhotel.app.features.home.HomeScreen
import com.kommhotel.app.features.room_detail.RoomDetailScreen
import com.kommhotel.app.features.splash.SplashScreen
import com.kommhotel.app.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

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

        LaunchedEffect(Unit) {
            initializeKoin()
            delay(2000)
            appState = AppState.LoggedOut
        }

        when (val state = appState) {
            is AppState.Loading -> SplashScreen()
            is AppState.LoggedOut -> LoginScreen(
                onLoginSuccess = { sessionId -> appState = AppState.LoggedIn(sessionId) },
                onRegisterClick = { appState = AppState.Registering }
            )
            is AppState.Registering -> RegisterScreen(
                onRegisterSuccess = { appState = AppState.LoggedOut }
            )
            is AppState.LoggedIn -> MainAuthenticatedNav(state.sessionId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainAuthenticatedNav(sessionId: String) {
    val savedStateConfiguration = SavedStateConfiguration {
        serializersModule = SerializersModule {
            polymorphic(NavKey::class) {
                subclass(Screen.Home::class)
                subclass(Screen.RoomDetail::class)
                subclass(Screen.MyBookings::class)
            }
        }
    }
    val backStack = rememberNavBackStack(savedStateConfiguration, Screen.Home)
    val currentScreen = backStack.lastOrNull()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val title = when (currentScreen) {
                        is Screen.Home -> "Home"
                        is Screen.RoomDetail -> "Room Details"
                        is Screen.MyBookings -> "My Bookings"
                        else -> "KommHotel"
                    }
                    Text(title)
                },
                navigationIcon = {
                    // Show back button only if there is a screen to go back to
                    if (backStack.size > 1) {
                        IconButton(onClick = { backStack.removeLastOrNull() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = currentScreen is Screen.Home || currentScreen is Screen.RoomDetail,
                    onClick = {
                        backStack.clear()
                        backStack.add(Screen.Home)
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "My Bookings") },
                    label = { Text("My Bookings") },
                    selected = currentScreen is Screen.MyBookings,
                    onClick = {
                        backStack.clear()
                        backStack.add(Screen.MyBookings)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {
                    is Screen.Home -> NavEntry(key) {
                        HomeScreen(onRoomClick = { roomId ->
                            backStack.add(Screen.RoomDetail(roomId))
                        })
                    }
                    is Screen.RoomDetail -> NavEntry(key) {
                        RoomDetailScreen(key.roomId)
                    }
                    is Screen.MyBookings -> NavEntry(key) {
                        MyBookingsScreen()
                    }
                    else -> NavEntry(key) { Text("Unknown Screen") }
                }
            }
        )
    }
}
