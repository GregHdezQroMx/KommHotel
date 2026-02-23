package com.kommhotel.app.features.bookings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.presentation.bookings.MyBookingsViewModel
import org.koin.compose.koinInject

@Composable
fun MyBookingsScreen() {
    val viewModel: MyBookingsViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.errorMessage != null -> {
                Text(text = uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
            }
            uiState.bookings.isEmpty() -> {
                Text("You have no bookings yet.")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.bookings) { booking ->
                        BookingItem(booking)
                    }
                }
            }
        }
    }
}

@Composable
fun BookingItem(booking: Booking) {
    Card(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Booking ID: ${booking.id}")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Room ID: ${booking.roomId}")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Check-in: ${booking.checkInDate}")
            Spacer(modifier = Modifier.height(4.dp))
            Text("Check-out: ${booking.checkOutDate}")
            Spacer(modifier = Modifier.height(8.dp))

            // Using theme colors for better consistency
            val statusColor = if (booking.status == com.kommhotel.shared.model.BookingStatus.CONFIRMED) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            Text("Status: ${booking.status}", color = statusColor)
        }
    }
}
