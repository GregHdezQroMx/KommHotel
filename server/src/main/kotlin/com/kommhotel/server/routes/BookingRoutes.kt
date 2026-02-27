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

fun Route.bookingRoutes() {
    authenticate("auth-jwt") {
        post("/bookings") {
            val principal = call.principal<JWTPrincipal>()
            val userEmail = principal?.payload?.getClaim("email")?.asString()

            if (userEmail == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                return@post
            }

            val user = userStorage[userEmail]?.first
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found in storage")
                return@post
            }

            val request = call.receive<CreateBookingRequest>()

            val newBooking = Booking(
                id = "booking_${UUID.randomUUID()}",
                guestId = user.id,
                roomId = request.roomId,
                checkInDate = request.checkInDate,
                checkOutDate = request.checkOutDate,
                totalPrice = 2400.0,
                status = BookingStatus.CONFIRMED
            )

            bookings.add(newBooking)
            call.respond(HttpStatusCode.Created, newBooking)
        }

        get("/me/bookings") {
            val principal = call.principal<JWTPrincipal>()
            val userEmail = principal?.payload?.getClaim("email")?.asString()

            // --- DEBUGGING LOGS ---
            println("--- DEBUG: /me/bookings ---")
            println("Token email: $userEmail")
            println("Current userStorage keys: ${userStorage.keys}")
            println("---------------------------")

            if (userEmail == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                return@get
            }

            val user = userStorage[userEmail]?.first
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "User not found in storage")
                return@get
            }

            val userBookings = bookings.filter { it.guestId == user.id }
            call.respond(userBookings)
        }
    }
}