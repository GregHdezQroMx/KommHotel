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
import kommhotel.shared.generated.resources.Res
import kommhotel.shared.generated.resources.auth_error_invalid_credentials
import kommhotel.shared.generated.resources.auth_error_user_exists
import kommhotel.shared.generated.resources.auth_error_user_not_found
import org.jetbrains.compose.resources.getString

// --- Request Models ---
@Serializable
data class RegisterRequest(val firstName: String, val lastName: String, val email: String, val password: String)

@Serializable
data class LoginRequest(val email: String, val password: String)

// --- In-Memory Storage (for demonstration purposes) ---
// WARNING: Password hashing is NOT implemented. This is a critical security step.
private val userStorage = mutableMapOf<String, Pair<Guest, String>>() // Email -> (Guest, Password)

fun Route.authRoutes() {
    /**
     * Handles user registration.
     */
    post("/auth/register") {
        val request = call.receive<RegisterRequest>()

        if (userStorage.containsKey(request.email)) {
            val errorMessage = getString(Res.string.auth_error_user_exists)
            call.respond(HttpStatusCode.Conflict, mapOf("error" to errorMessage))
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

        // TODO: Implement proper password hashing (e.g., BCrypt) before storing.
        userStorage[request.email] = newGuest to request.password

        // TODO: Generate a real JWT (JSON Web Token).
        val token = "fake-jwt-for-${newGuest.id}"
        call.respond(mapOf("token" to token))
    }

    /**
     * Handles user login.
     */
    post("/auth/login") {
        val request = call.receive<LoginRequest>()

        val userRecord = userStorage[request.email]
        if (userRecord == null) {
            val errorMessage = getString(Res.string.auth_error_user_not_found)
            call.respond(HttpStatusCode.NotFound, mapOf("error" to errorMessage))
            return@post
        }

        // TODO: Use a secure hash comparison instead of plain text.
        val (guest, storedPassword) = userRecord
        if (storedPassword == request.password) {
            // TODO: Generate a real JWT (JSON Web Token).
            val token = "fake-jwt-for-${guest.id}"
            call.respond(mapOf("token" to token))
        } else {
            val errorMessage = getString(Res.string.auth_error_invalid_credentials)
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to errorMessage))
        }
    }
}