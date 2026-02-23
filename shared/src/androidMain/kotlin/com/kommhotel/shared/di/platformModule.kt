package com.kommhotel.shared.di

import com.kommhotel.shared.data.local.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * The actual implementation of the platform-specific Koin module for Android.
 * It provides an implementation for the SessionManager using Android's DataStore.
 */
actual val platformModule = module {
    single { SessionManager(androidContext()) }
}
