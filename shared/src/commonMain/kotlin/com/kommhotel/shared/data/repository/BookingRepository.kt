package com.kommhotel.shared.data.repository

import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.util.getBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

// --- DTO for creating a booking ---
@Serializable
data class CreateBookingRequest(
    val roomId: String,
    val checkInDate: String,
    val checkOutDate: String
)

// --- Repository ---

interface BookingRepository {
    suspend fun createBooking(request: CreateBookingRequest): Result<Booking>
    suspend fun getMyBookings(): Result<List<Booking>> // <-- ADDED
}

class BookingRepositoryImpl(private val httpClient: HttpClient) : BookingRepository {

    override suspend fun createBooking(request: CreateBookingRequest): Result<Booking> {
        return try {
            val response = httpClient.post("${getBaseUrl()}/bookings") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            Result.success(response.body())
        } catch (e: Exception) {
            println("BookingRepository Error: $e")
            Result.failure(e)
        }
    }

    // Implementation for the new function
    override suspend fun getMyBookings(): Result<List<Booking>> {
        return try {
            val response = httpClient.get("${getBaseUrl()}/me/bookings")
            Result.success(response.body())
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.NotFound) {
                Result.success(emptyList()) // Return empty list on 404
            } else {
                Result.failure(e) // Re-throw other client errors
            }
        } catch (e: Exception) {
            println("BookingRepository Error: $e")
            Result.failure(e)
        }
    }
}