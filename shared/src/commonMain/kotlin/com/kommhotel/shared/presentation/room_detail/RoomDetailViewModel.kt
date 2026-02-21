package com.kommhotel.shared.presentation.room_detail

import com.kommhotel.shared.data.repository.BookingRepository
import com.kommhotel.shared.data.repository.CreateBookingRequest
import com.kommhotel.shared.data.repository.RoomRepository
import com.kommhotel.shared.model.Room
import com.kommhotel.shared.presentation.login.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject

data class RoomDetailUiState(
    val isLoading: Boolean = false,
    val room: Room? = null,
    val errorMessage: String? = null,
    // Booking state
    val isBooking: Boolean = false,
    val bookingSuccess: Boolean = false,
    val bookingError: String? = null
)

class RoomDetailViewModel(private val roomId: String) : ViewModel() {

    private val roomRepository: RoomRepository by inject()
    private val bookingRepository: BookingRepository by inject() // Injected BookingRepository
    override val scope = CoroutineScope(Dispatchers.Main)

    private val _uiState = MutableStateFlow(RoomDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRoomDetails()
    }

    fun loadRoomDetails() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = roomRepository.getRoomById(roomId)

            result.onSuccess { room ->
                _uiState.update { it.copy(isLoading = false, room = room) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.message ?: "An unknown error occurred") }
            }
        }
    }

    // New function to handle the booking action
    fun bookRoom() {
        scope.launch {
            _uiState.update { it.copy(isBooking = true, bookingError = null, bookingSuccess = false) }

            // For now, we use placeholder dates. We will implement a date picker later.
            val request = CreateBookingRequest(
                roomId = roomId,
                checkInDate = "2024-10-20",
                checkOutDate = "2024-10-22"
            )

            val result = bookingRepository.createBooking(request)

            result.onSuccess { booking ->
                _uiState.update { it.copy(isBooking = false, bookingSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isBooking = false, bookingError = error.message ?: "Booking failed") }
            }
        }
    }
}