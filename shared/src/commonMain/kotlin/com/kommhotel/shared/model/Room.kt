package com.kommhotel.shared.model

/**
 * Represents a hotel room with a flexible pricing model.
 *
 * @property id Unique room identifier.
 * @property name Descriptive name for the room (e.g., "Presidential Suite").
 * @property number Room number (e.g., "101", "203B").
 * @property type Type of room (e.g., "Single", "Double", "Suite").
 * @property description Brief description of the room and its amenities.
 * @property capacity Maximum number of guests it can accommodate.
 * @property pricing The pricing model that applies to this room (per-night or per-hour).
 * @property isAvailable Current availability status.
 */
data class Room(
    val id: String,
    val name: String,
    val number: String,
    val type: String,
    val description: String,
    val capacity: Int,
    val pricing: RoomPricing,
    val isAvailable: Boolean
)