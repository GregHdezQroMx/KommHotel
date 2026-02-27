package com.kommhotel.server

// Centralized configuration for JWT tokens
object JwtConfig {
    const val secret = "my-super-secret-for-jwt"
    const val issuer = "http://0.0.0.0:8080"
    const val audience = "users"
    const val realm = "KommHotel"
}
