package com.kommhotel.shared.presentation.bookings

import com.kommhotel.shared.data.repository.BookingRepository
import com.kommhotel.shared.model.Booking
import com.kommhotel.shared.presentation.login.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject

data class MyBookingsUiState(
    val isLoading: Boolean = true,
    val bookings: List<Booking> = emptyList(),
    val errorMessage: String? = null
)

class MyBookingsViewModel : ViewModel() {

    private val bookingRepository: BookingRepository by inject()

    private val _uiState = MutableStateFlow(MyBookingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMyBookings()
    }

    fun loadMyBookings() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val result = bookingRepository.getMyBookings()

            result.onSuccess { bookings ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        bookings = bookings,
                        errorMessage = null
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "An unknown error occurred"
                    )
                }
            }
        }
    }
}