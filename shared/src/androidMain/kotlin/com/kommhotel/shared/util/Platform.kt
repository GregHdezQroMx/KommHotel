package com.kommhotel.shared.util

import android.os.Build
import com.kommhotel.shared.BuildConfig

private val isEmulator: Boolean by lazy {
    // A more robust check for emulators
    return@lazy (Build.FINGERPRINT.startsWith("generic")
            || Build.FINGERPRINT.startsWith("unknown")
            || Build.MODEL.contains("google_sdk")
            || Build.MODEL.contains("Emulator")
            || Build.MODEL.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
            || "google_sdk" == Build.PRODUCT)
}

/**
 * Returns the correct base URL for the server depending on the environment.
 */
actual fun getBaseUrl(): String {
    return if (BuildConfig.DEBUG) {
        if (isEmulator) {
            "http://10.0.2.2:8080" // Magic IP for localhost from Android emulator
        } else {
            "http://192.168.1.73:8080" // Your Mac's local IP for physical devices
        }
    } else {
        "https://your-production-domain.com" // Placeholder for production
    }
}