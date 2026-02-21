package com.kommhotel.app.features.room_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kommhotel.shared.model.RoomPricing
import com.kommhotel.shared.presentation.room_detail.RoomDetailViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun RoomDetailScreen(roomId: String) {
    // Use koinInject and pass the roomId as a parameter to the ViewModel's constructor
    val viewModel: RoomDetailViewModel = koinInject(parameters = { parametersOf(roomId) })
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.errorMessage != null -> {
                Text(uiState.errorMessage!!, color = Color.Red)
            }
            uiState.room != null -> {
                val room = uiState.room!!
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(room.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Room #${room.number} - ${room.type}", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(room.description)
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    val priceText = when (val pricing = room.pricing) {
                        is RoomPricing.PerNight -> "$${pricing.pricePerNight} / night"
                        is RoomPricing.PerHour -> "$${pricing.basePrice} for first ${pricing.includedHours}h"
                    }
                    Text("Price: $priceText", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}