package com.kommhotel.server.routes

import com.kommhotel.shared.data.repository.CreateBookingRequest
import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.model.BookingStatus
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.util.UUID

// A simple in-memory store for bookings
private val bookings = mutableListOf<Booking>()

fun Route.bookingRoutes() {
    post("/bookings") {
        val request = call.receive<CreateBookingRequest>()

        // TODO: In a real app, you would:
        // 1. Get the current user's ID from their auth token.
        // 2. Find the room in the database to get its real price.
        // 3. Calculate the real total price based on dates.

        // For now, we create a dummy booking and return it.
        val newBooking = Booking(
            id = "booking_${UUID.randomUUID()}",
            guestId = "user_1", // Hardcoded guest ID for now
            roomId = request.roomId,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = 2400.0, // Hardcoded price for now
            status = BookingStatus.CONFIRMED
        )

        bookings.add(newBooking)

        call.respond(HttpStatusCode.Created, newBooking)
    }
}
