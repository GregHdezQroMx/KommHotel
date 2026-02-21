package com.kommhotel.shared.presentation.register

import com.kommhotel.shared.data.repository.AuthRepository
import com.kommhotel.shared.data.repository.RegisterRequest
import com.kommhotel.shared.presentation.login.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject

data class RegisterUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registrationSuccess: Boolean = false
)

class RegisterViewModel : ViewModel() {

    private val authRepository: AuthRepository by inject()
    override val scope = CoroutineScope(Dispatchers.Main)

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onFirstNameChange(text: String) {
        _uiState.update { it.copy(firstName = text, errorMessage = null) }
    }

    fun onLastNameChange(text: String) {
        _uiState.update { it.copy(lastName = text, errorMessage = null) }
    }

    fun onEmailChange(text: String) {
        _uiState.update { it.copy(email = text, errorMessage = null) }
    }

    fun onPasswordChange(text: String) {
        _uiState.update { it.copy(password = text, errorMessage = null) }
    }

    fun register() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val request = RegisterRequest(
                firstName = _uiState.value.firstName,
                lastName = _uiState.value.lastName,
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            val result = authRepository.register(request)

            result.onSuccess { response ->
                if (response.token != null) {
                    // SUCCESS: Registration was successful.
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            registrationSuccess = true
                        )
                    }
                } else {
                    // BUSINESS ERROR: Server returned an error message.
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = response.error ?: "An unknown server error occurred"
                        )
                    }
                }
            }.onFailure { error ->
                // NETWORK/PARSING ERROR
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
