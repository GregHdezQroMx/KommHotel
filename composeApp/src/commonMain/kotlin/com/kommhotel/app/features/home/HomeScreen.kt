package com.kommhotel.app.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kommhotel.shared.model.Room
import com.kommhotel.shared.model.RoomPricing
import com.kommhotel.shared.presentation.home.HomeViewModel
import org.koin.compose.koinInject

@Composable
fun HomeScreen(onRoomClick: (String) -> Unit) { // Added navigation callback
    val viewModel: HomeViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage ?: "Unknown error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.rooms) { room ->
                    RoomItem(room, onClick = { onRoomClick(room.id) }) // Pass room id on click
                }
            }
        }
    }
}

@Composable
private fun RoomItem(room: Room, onClick: () -> Unit) { // Added click callback
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Made the card clickable
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = room.name, fontWeight = FontWeight.Bold)
            Text(text = "Capacity: ${room.capacity}")

            val priceText = when (val pricing = room.pricing) {
                is RoomPricing.PerNight -> "$${pricing.pricePerNight} per night"
                is RoomPricing.PerHour -> "$${pricing.basePrice} for ${pricing.includedHours} hours"
            }
            Text(text = "Price: $priceText")
        }
    }
}
