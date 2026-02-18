package com.kommhotel.shared.model

/**
 * Represents a hotel guest, including their personal preferences.
 *
 * @property id Unique guest identifier, possibly from the authentication system.
 * @property firstName Guest's first name.
 * @property lastName Guest's last name.
 * @property email Email address for confirmations and marketing.
 * @property phoneNumber Contact phone number.
 * @property preferences The guest's preference profile for personalized service.
 */
data class Guest(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val preferences: GuestPreferences = GuestPreferences()
)