package com.kommhotel.server.routes

import com.kommhotel.shared.data.repository.CreateBookingRequest
import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.model.BookingStatus
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import java.util.UUID

// A simple in-memory store for bookings
private val bookings = mutableListOf<Booking>()

fun Route.bookingRoutes() {
    // This is a public route to create a booking
    post("/bookings") {
        val request = call.receive<CreateBookingRequest>()

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

    // This is a protected route group
    authenticate("auth-jwt") {
        get("/me/bookings") {
            val principal = call.principal<JWTPrincipal>()
            val userEmail = principal?.payload?.getClaim("email")?.asString()

            if (userEmail == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                return@get
            }

            // In a real app, you would query the database for bookings where guestId matches the user's ID.
            // For now, we return all bookings as a placeholder.
            call.respond(bookings)
        }
    }
}
