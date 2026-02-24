package com.kommhotel.shared.util

/**
 * JS-specific implementation to get the base URL.
 * It uses the standard loopback IP address.
 */
actual fun getBaseUrl(): String = "http://127.0.0.1:8080"
