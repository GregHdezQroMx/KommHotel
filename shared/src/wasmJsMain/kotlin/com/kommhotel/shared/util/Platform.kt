package com.kommhotel.shared.util

/**
 * Returns the base URL for the server when running on the web (WebAssembly).
 */
actual fun getBaseUrl(): String = "http://localhost:8080"
