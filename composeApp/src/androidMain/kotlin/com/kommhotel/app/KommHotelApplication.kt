package com.kommhotel.app

import android.app.Application
import com.kommhotel.shared.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

/**
 * Custom Application class for the Android app.
 * This is the correct entry point to initialize Koin with the Android Context.
 */
class KommHotelApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            // Provides the Android context to Koin
            androidContext(this@KommHotelApplication)
            // Optional: Use the Android logger for Koin logs
            androidLogger()
        }
    }
}
