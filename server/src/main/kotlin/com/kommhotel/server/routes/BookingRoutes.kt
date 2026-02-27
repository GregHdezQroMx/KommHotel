package com.kommhotel.server.routes

import com.kommhotel.server.repository.BookingRepository
import com.kommhotel.server.repository.UserRepository
import com.kommhotel.shared.data.repository.CreateBookingRequest
import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.model.BookingStatus
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import java.util.UUID

fun Route.bookingRoutes(userRepository: UserRepository, bookingRepository: BookingRepository) {
    authenticate("auth-jwt") {
        post("/bookings") {
            val principal = call.principal<JWTPrincipal>()
            val userEmail = principal?.payload?.getClaim("email")?.asString()

            if (userEmail == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                return@post
            }

            val userRecord = userRepository.findUserByEmail(userEmail)
            if (userRecord == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
                return@post
            }
            val (user, _) = userRecord

            val request = call.receive<CreateBookingRequest>()

            val newBooking = Booking(
                id = "booking_${UUID.randomUUID()}",
                guestId = user.id,
                roomId = request.roomId,
                checkInDate = request.checkInDate,
                checkOutDate = request.checkOutDate,
                totalPrice = 2400.0, // TODO: Calculate price based on room and dates
                status = BookingStatus.CONFIRMED
            )

            bookingRepository.createBooking(newBooking)
            call.respond(HttpStatusCode.Created, newBooking)
        }

        get("/me/bookings") {
            val principal = call.principal<JWTPrincipal>()
            val userEmail = principal?.payload?.getClaim("email")?.asString()

            if (userEmail == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid token")
                return@get
            }

            val userRecord = userRepository.findUserByEmail(userEmail)
            if (userRecord == null) {
                call.respond(HttpStatusCode.NotFound, "User not found")
                return@get
            }
            val (user, _) = userRecord

            val userBookings = bookingRepository.getBookingsByUserId(user.id)
            call.respond(userBookings)
        }
    }
}