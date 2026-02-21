package com.kommhotel.shared.util

/**
 * Returns the base URL for the server when running on an iOS device or simulator.
 */
actual fun getBaseUrl(): String = "http://192.168.1.73:8080"
