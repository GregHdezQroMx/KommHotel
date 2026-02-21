package com.kommhotel.shared.util

/**
 * Returns the base URL for the server when running on a desktop JVM.
 * We use the explicit network IP to avoid potential localhost/firewall issues on desktop.
 */
actual fun getBaseUrl(): String = "http://192.168.1.73:8080"
