package com.kommhotel.shared.util

/**
 * Android-specific implementation to get the base URL.
 * It uses the special loopback IP (10.0.2.2) to connect to the host machine's localhost from the Android Emulator.
 * For physical device testing, this would need to be changed to the host's network IP.
 */
actual fun getBaseUrl(): String = "http://10.0.2.2:8080"
