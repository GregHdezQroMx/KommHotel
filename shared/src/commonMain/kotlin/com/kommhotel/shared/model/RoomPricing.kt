package com.kommhotel.shared.model

/**
 * Defines the pricing model for a room, allowing for per-night or per-hour strategies.
 */
sealed class RoomPricing {
    /**
     * Pricing model for traditional (tourist) hotels.
     * @property pricePerNight Fixed cost for each night's stay.
     */
    data class PerNight(val pricePerNight: Double) : RoomPricing()

    /**
     * Pricing model for short-stay or transit hotels.
     * @property basePrice Initial cost for the first block of hours.
     * @property includedHours Number of hours included in the base cost.
     * @property extraHourPrice Cost for each additional hour after the initial block.
     */
    data class PerHour(
        val basePrice: Double,
        val includedHours: Int,
        val extraHourPrice: Double
    ) : RoomPricing()
}