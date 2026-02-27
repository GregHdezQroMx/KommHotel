package com.kommhotel.server.routes

import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.model.Guest

// In-memory storage, centralized for access from multiple route files.
internal val userStorage = mutableMapOf<String, Pair<Guest, String>>() // Email -> (Guest, Password)
internal val bookings = mutableListOf<Booking>()
