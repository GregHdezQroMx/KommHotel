package com.kommhotel.server.routes

import com.kommhotel.shared.model.Room
import com.kommhotel.shared.model.RoomPricing
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

// A simple in-memory store for rooms
private val sampleRooms = listOf(
    Room(
        id = "101",
        name = "Standard Single",
        number = "101",
        type = "Single",
        description = "A cozy room for a single traveler with a city view.",
        capacity = 1,
        pricing = RoomPricing.PerNight(pricePerNight = 1200.0),
        isAvailable = true
    ),
    Room(
        id = "202",
        name = "Deluxe Double",
        number = "202",
        type = "Double",
        description = "A spacious room with a queen-sized bed, perfect for couples.",
        capacity = 2,
        pricing = RoomPricing.PerNight(pricePerNight = 2500.0),
        isAvailable = true
    ),
    Room(
        id = "305",
        name = "Executive Suite",
        number = "305",
        type = "Suite",
        description = "A luxurious suite with a separate living area and premium amenities.",
        capacity = 2,
        pricing = RoomPricing.PerNight(pricePerNight = 4500.0),
        isAvailable = false
    ),
    Room(
        id = "M01",
        name = "Transit Stop",
        number = "M01",
        type = "Hourly",
        description = "A convenient room for short stays, located near the main lobby.",
        capacity = 2,
        pricing = RoomPricing.PerHour(basePrice = 500.0, includedHours = 3, extraHourPrice = 150.0),
        isAvailable = true
    )
)

fun Route.roomRoutes() {
    // Route to get all rooms
    get("/rooms") {
        call.respond(sampleRooms)
    }

    // Route to get a single room by its ID
    get("/rooms/{id}") {
        val id = call.parameters["id"]
        val room = sampleRooms.find { it.id == id }
        if (room != null) {
            call.respond(room)
        } else {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Room not found"))
        }
    }
}