package com.kommhotel.server.routes

import com.kommhotel.shared.model.Room
import com.kommhotel.shared.model.RoomPricing
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

// Por ahora, usaremos datos en memoria. Esto sería reemplazado por una base de datos.
private val sampleRooms = listOf(
    Room(
        id = "101",
        name = "Standard Single",
        number = "101",
        type = "Single",
        description = "A cozy room for a single traveler.",
        capacity = 1,
        pricing = RoomPricing.PerNight(pricePerNight = 1200.0),
        isAvailable = true
    ),
    Room(
        id = "202",
        name = "Deluxe Double",
        number = "202",
        type = "Double",
        description = "A spacious room with a queen-sized bed.",
        capacity = 2,
        pricing = RoomPricing.PerNight(pricePerNight = 2500.0),
        isAvailable = true
    ),
    Room(
        id = "305",
        name = "Executive Suite",
        number = "305",
        type = "Suite",
        description = "A luxurious suite with a separate living area.",
        capacity = 2,
        pricing = RoomPricing.PerNight(pricePerNight = 4500.0),
        isAvailable = false
    ),
    Room(
        id = "M01",
        name = "Transit Stop",
        number = "M01",
        type = "Hourly",
        description = "A room for short stays.",
        capacity = 2,
        pricing = RoomPricing.PerHour(basePrice = 500.0, includedHours = 3, extraHourPrice = 150.0),
        isAvailable = true
    )
)

fun Route.roomRoutes() {
    get("/rooms") {
        call.respond(sampleRooms)
    }
}