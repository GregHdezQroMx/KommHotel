package com.kommhotel.server.routes

import com.kommhotel.shared.model.Guest
import com.kommhotel.shared.model.GuestPreferences
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable

// --- Request Models ---
@Serializable
data class RegisterRequest(val firstName: String, val lastName: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val email: String, val password: String)

// --- In-Memory Storage (for demonstration purposes) ---
private val userStorage = mutableMapOf<String, Pair<Guest, String>>() // Email -> (Guest, Password)

fun Route.authRoutes() {
    /**
     * Handles user registration.
     */
    post("/auth/register") {
        val request = call.receive<RegisterRequest>()

        if (userStorage.containsKey(request.email)) {
            // Business error, but the request was processed successfully.
            call.respond(HttpStatusCode.OK, mapOf("error" to "User with this email already exists."))
            return@post
        }

        val newGuest = Guest(
            id = "user_${userStorage.size + 1}", // Simple ID generation
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            phoneNumber = "", // To be added later
            preferences = GuestPreferences()
        )

        userStorage[request.email] = newGuest to request.password

        val token = "fake-jwt-for-${newGuest.id}"
        call.respond(HttpStatusCode.OK, mapOf("token" to token))
    }

    /**
     * Handles user login.
     */
    post("/auth/login") {
        val request = call.receive<LoginRequest>()

        val userRecord = userStorage[request.email]
        if (userRecord == null) {
            // Business error, but the request was processed successfully.
            call.respond(HttpStatusCode.OK, mapOf("error" to "User not found."))
            return@post
        }

        val (guest, storedPassword) = userRecord
        if (storedPassword == request.password) {
            val token = "fake-jwt-for-${guest.id}"
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        } else {
            // Business error, but the request was processed successfully.
            call.respond(HttpStatusCode.OK, mapOf("error" to "Invalid credentials."))
        }
    }
}