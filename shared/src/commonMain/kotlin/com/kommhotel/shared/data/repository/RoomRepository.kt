package com.kommhotel.shared.data.repository

import com.kommhotel.shared.model.Room
import com.kommhotel.shared.util.getBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

// --- Repository ---

interface RoomRepository {
    suspend fun getRooms(): Result<List<Room>>
}

class RoomRepositoryImpl(private val httpClient: HttpClient) : RoomRepository {

    override suspend fun getRooms(): Result<List<Room>> {
        return try {
            // The correct endpoint is /rooms, not /rooms/all
            val response = httpClient.get("${getBaseUrl()}/rooms")
            Result.success(response.body())
        } catch (e: Exception) {
            println("RoomRepository Error: $e")
            Result.failure(e)
        }
    }
}
