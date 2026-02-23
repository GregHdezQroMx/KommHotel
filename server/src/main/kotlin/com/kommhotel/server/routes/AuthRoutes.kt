package com.kommhotel.server.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kommhotel.shared.data.repository.AuthResponse // <-- IMPORT THE CORRECT RESPONSE DTO
import com.kommhotel.shared.model.Guest
import com.kommhotel.shared.model.GuestPreferences
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import java.util.Date

// --- Request Models ---
@Serializable
data class RegisterRequest(val firstName: String, val lastName: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val email: String, val password: String)

// --- In-Memory Storage (for demonstration purposes) ---
private val userStorage = mutableMapOf<String, Pair<Guest, String>>() // Email -> (Guest, Password)

fun Route.authRoutes() {
    val secret = "my-super-secret-for-jwt"
    val issuer = "http://0.0.0.0:8080"
    val audience = "users"

    post("/auth/register") {
        val request = call.receive<RegisterRequest>()

        if (userStorage.containsKey(request.email)) {
            call.respond(HttpStatusCode.OK, AuthResponse(error = "User with this email already exists."))
            return@post
        }

        val newGuest = Guest(
            id = "user_${userStorage.size + 1}",
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            phoneNumber = "",
            preferences = GuestPreferences()
        )

        userStorage[request.email] = newGuest to request.password

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", newGuest.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000)) // 1 day
            .sign(Algorithm.HMAC256(secret))

        call.respond(HttpStatusCode.OK, AuthResponse(token = token))
    }

    post("/auth/login") {
        val request = call.receive<LoginRequest>()

        val userRecord = userStorage[request.email]
        if (userRecord == null) {
            call.respond(HttpStatusCode.OK, AuthResponse(error = "User not found."))
            return@post
        }

        val (guest, storedPassword) = userRecord
        if (storedPassword == request.password) {
            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("email", guest.email)
                .withExpiresAt(Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000)) // 1 day
                .sign(Algorithm.HMAC256(secret))

            call.respond(HttpStatusCode.OK, AuthResponse(token = token))
        } else {
            call.respond(HttpStatusCode.OK, AuthResponse(error = "Invalid credentials."))
        }
    }
}