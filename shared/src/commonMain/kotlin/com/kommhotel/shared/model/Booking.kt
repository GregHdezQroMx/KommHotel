package com.kommhotel.shared.model

/**
 * Represents the booking of a room by a guest for a specific period.
 *
 * @property id Unique booking identifier.
 * @property guest The guest who made the booking.
 * @property room The room that has been booked.
 * @property checkInTimestamp Start date and time of the booking (in UTC milliseconds).
 * @property checkOutTimestamp End date and time of the booking (in UTC milliseconds).
 * @property totalCost The total calculated cost for the stay.
 * @property status The current status of the booking in its lifecycle.
 * @property specialRequests Any special requests for this particular booking (in addition to the guest's preferences).
 */
data class Booking(
    val id: String,
    val guest: Guest,
    val room: Room,
    val checkInTimestamp: Long,
    val checkOutTimestamp: Long,
    val totalCost: Double,
    val status: BookingStatus,
    val specialRequests: List<String> = emptyList()
)

/**
 * Defines the lifecycle of a booking.
 */
enum class BookingStatus {
    PENDING,      // The booking is created but not confirmed (e.g., awaiting payment)
    CONFIRMED,    // Payment has been processed and the room is reserved
    CHECKED_IN,   // The guest has arrived and is occupying the room
    CHECKED_OUT,  // The guest has completed their stay and left
    CANCELLED     // The booking has been cancelled
}