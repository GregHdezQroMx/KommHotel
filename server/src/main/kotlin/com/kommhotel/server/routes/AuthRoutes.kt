package com.kommhotel.server.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kommhotel.server.JwtConfig
import com.kommhotel.server.repository.UserRepository
import com.kommhotel.shared.data.repository.AuthResponse
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
import java.util.UUID

@Serializable
data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String? = ""
)

@Serializable
data class LoginRequest(val email: String, val password: String)

fun Route.authRoutes(userRepository: UserRepository) {
    post("/auth/register") {
        val request = try {
            call.receive<RegisterRequest>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, AuthResponse(error = "Invalid request format"))
            return@post
        }

        if (userRepository.findUserByEmail(request.email) != null) {
            call.respond(HttpStatusCode.OK, AuthResponse(error = "User with this email already exists."))
            return@post
        }

        // Hashear la contraseña antes de guardarla
        val hashedPassword = BCrypt.withDefaults().hashToString(12, request.password.toCharArray())

        val newGuest = Guest(
            id = "user_${UUID.randomUUID()}",
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            phoneNumber = request.phoneNumber ?: "",
            preferences = GuestPreferences()
        )

        userRepository.createUser(newGuest, hashedPassword)

        val token = JWT.create()
            .withAudience(JwtConfig.audience)
            .withIssuer(JwtConfig.issuer)
            .withClaim("email", newGuest.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
            .sign(Algorithm.HMAC256(JwtConfig.secret))

        call.respond(HttpStatusCode.OK, AuthResponse(token = token))
    }

    post("/auth/login") {
        val request = call.receive<LoginRequest>()

        val userRecord = userRepository.findUserByEmail(request.email)
        if (userRecord == null) {
            call.respond(HttpStatusCode.OK, AuthResponse(error = "User not found."))
            return@post
        }

        val (guest, storedHash) = userRecord
        
        // Verificar la contraseña usando BCrypt
        val result = BCrypt.verifyer().verify(request.password.toCharArray(), storedHash)
        
        if (result.verified) {
            val token = JWT.create()
                .withAudience(JwtConfig.audience)
                .withIssuer(JwtConfig.issuer)
                .withClaim("email", guest.email)
                .withExpiresAt(Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .sign(Algorithm.HMAC256(JwtConfig.secret))

            call.respond(HttpStatusCode.OK, AuthResponse(token = token))
        } else {
            call.respond(HttpStatusCode.OK, AuthResponse(error = "Invalid credentials."))
        }
    }
}
