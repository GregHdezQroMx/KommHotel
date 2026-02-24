package com.kommhotel.shared.util

/**
 * iOS-specific implementation to get the base URL.
 * It uses the standard loopback IP address, which works for simulators and devices
 * on the same Wi-Fi network as the server.
 */
actual fun getBaseUrl(): String = "http://127.0.0.1:8080"
