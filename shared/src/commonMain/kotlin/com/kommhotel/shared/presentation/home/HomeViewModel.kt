package com.kommhotel.shared.presentation.home

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

data class HomeUiState(
    val isLoading: Boolean = false,
    val rooms: List<Room> = emptyList(),
    val errorMessage: String? = null
)

class HomeViewModel : ViewModel() {

    private val roomRepository: RoomRepository by inject()
    override val scope = CoroutineScope(Dispatchers.Main)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRooms()
    }

    fun loadRooms() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = roomRepository.getRooms()

            result.onSuccess { rooms ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rooms = rooms
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
