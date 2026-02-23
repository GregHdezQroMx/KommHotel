package com.kommhotel.shared.di

import com.kommhotel.shared.data.local.SessionManager
import org.koin.dsl.module

/**
 * The actual implementation of the platform-specific Koin module for the iOS platform.
 * It provides a simple factory for the iOS-based SessionManager.
 */
actual val platformModule = module {
    single { SessionManager() }
}