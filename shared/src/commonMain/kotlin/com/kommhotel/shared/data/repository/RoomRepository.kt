package com.kommhotel.shared.data.repository

import com.kommhotel.shared.model.Room
import com.kommhotel.shared.util.getBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface RoomRepository {
    suspend fun getRooms(): Result<List<Room>>
    suspend fun getRoomById(id: String): Result<Room> // New function
}

class RoomRepositoryImpl(private val httpClient: HttpClient) : RoomRepository {

    override suspend fun getRooms(): Result<List<Room>> {
        return try {
            val response = httpClient.get("${getBaseUrl()}/rooms")
            Result.success(response.body())
        } catch (e: Exception) {
            println("RoomRepository Error: $e")
            Result.failure(e)
        }
    }

    override suspend fun getRoomById(id: String): Result<Room> {
        return try {
            val response = httpClient.get("${getBaseUrl()}/rooms/$id")
            Result.success(response.body())
        } catch (e: Exception) {
            println("RoomRepository Error: $e")
            Result.failure(e)
        }
    }
}