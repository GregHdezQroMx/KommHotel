package com.kommhotel.shared.data.repository

import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.util.getBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
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
}