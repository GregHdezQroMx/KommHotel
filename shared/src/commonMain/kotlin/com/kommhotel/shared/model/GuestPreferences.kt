package com.kommhotel.shared.model

/**
 * Encapsulates a guest's personal preferences for a tailored experience.
 *
 * @property tripPurpose The main reason for the trip (Business, Leisure), to customize offers.
 * @property allergies List of known allergies (e.g., "Gluten", "Lactose", "Peanuts").
 * @property roomPreferences Preferences regarding room location or features (e.g., "High floor", "Near elevator").
 * @property interests List of interests for local recommendations (e.g., "Gastronomy", "Art", "Hiking").
 * @property recurringRequests Special requests the guest often makes (e.g., "Extra pillows", "Baby crib").
 */
data class GuestPreferences(
    val tripPurpose: TripPurpose? = null,
    val allergies: List<String> = emptyList(),
    val roomPreferences: List<String> = emptyList(),
    val interests: List<String> = emptyList(),
    val recurringRequests: List<String> = emptyList()
)

/**
 * Defines the main purpose of a guest's trip.
 */
enum class TripPurpose {
    BUSINESS,
    LEISURE,
    FAMILY,
    OTHER
}