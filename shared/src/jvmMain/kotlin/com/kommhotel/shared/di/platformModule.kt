package com.kommhotel.shared.di

import com.kommhotel.shared.data.local.SessionManager
import org.koin.dsl.module

/**
 * The actual implementation of the platform-specific Koin module for the JVM platform.
 * It provides a simple factory for the in-memory SessionManager.
 */
actual val platformModule = module {
    single { SessionManager() }
}
