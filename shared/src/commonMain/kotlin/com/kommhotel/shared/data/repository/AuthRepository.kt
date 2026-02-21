package com.kommhotel.shared.data.repository

import com.kommhotel.shared.util.getBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

// --- Data Transfer Objects (DTOs) ---

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class RegisterRequest(val firstName: String, val lastName: String, val email: String, val password: String)

@Serializable
data class AuthResponse(
    val token: String? = null, // Default to null if missing
    val error: String? = null  // Default to null if missing
)

// --- Repository ---

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<AuthResponse>
    suspend fun register(request: RegisterRequest): Result<AuthResponse>
}

class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {

    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return try {
            val response = httpClient.post("${getBaseUrl()}/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            println("AuthRepository Error: $e") // <-- ADDED FOR DEBUGGING
            Result.failure(e)
        }
    }

    override suspend fun register(request: RegisterRequest): Result<AuthResponse> {
        return try {
            val response = httpClient.post("${getBaseUrl()}/auth/register") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            println("AuthRepository Error: $e") // <-- ADDED FOR DEBUGGING
            Result.failure(e)
        }
    }
}