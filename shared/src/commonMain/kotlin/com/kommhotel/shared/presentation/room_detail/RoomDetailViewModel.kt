package com.kommhotel.shared.presentation.room_detail

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
    val errorMessage: String? = null
)

class RoomDetailViewModel(private val roomId: String) : ViewModel() {

    private val roomRepository: RoomRepository by inject()
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
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        room = room
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
