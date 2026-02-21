package com.kommhotel.shared.model

import kotlinx.serialization.Serializable

/**
 * Defines the lifecycle of a booking.
 */
@Serializable
enum class BookingStatus {
    PENDING,      // The booking is created but not confirmed (e.g., awaiting payment)
    CONFIRMED,    // Payment has been processed and the room is reserved
    CHECKED_IN,   // The guest has arrived and is occupying the room
    CHECKED_OUT,  // The guest has completed their stay and left
    CANCELLED     // The booking has been cancelled
}

/**
 * Represents a booking made by a guest for a specific room.
 *
 * @property id Unique booking identifier.
 * @property guestId The ID of the guest who made the booking.
 * @property roomId The ID of the booked room.
 * @property checkInDate The check-in date (e.g., in ISO 8601 format "YYYY-MM-DD").
 * @property checkOutDate The check-out date (e.g., in ISO 8601 format "YYYY-MM-DD").
 * @property totalPrice The total price calculated for the stay.
 * @property status The current status of the booking.
 */
@Serializable
data class Booking(
    val id: String,
    val guestId: String,
    val roomId: String,
    val checkInDate: String,
    val checkOutDate: String,
    val totalPrice: Double,
    val status: BookingStatus // Using the correct, type-safe enum
)
