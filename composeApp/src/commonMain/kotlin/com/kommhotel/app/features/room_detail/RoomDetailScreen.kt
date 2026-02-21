package com.kommhotel.app.features.room_detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import kommhotel.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun RoomDetailScreen(roomId: String) {
    val viewModel: RoomDetailViewModel = koinInject(parameters = { parametersOf(roomId) })
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
        AnimatedContent(targetState = uiState) { state ->
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }
                state.errorMessage != null -> {
                    Text(state.errorMessage!!, color = Color.Red)
                }
                state.room != null -> {
                    val room = state.room!!
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

                        Spacer(modifier = Modifier.weight(1.0f))

                        // Booking status section
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            state.bookingError?.let {
                                Text(it, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
                            }
                            if (state.bookingSuccess) {
                                Text("Room booked successfully!", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 8.dp))
                            }

                            if (state.isBooking) {
                                CircularProgressIndicator()
                            } else {
                                Button(
                                    onClick = { viewModel.bookRoom() },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(stringResource(Res.string.button_book_now))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}