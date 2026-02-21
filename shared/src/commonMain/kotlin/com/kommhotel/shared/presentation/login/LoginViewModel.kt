package com.kommhotel.shared.presentation.login

import com.kommhotel.shared.data.repository.AuthRepository
import com.kommhotel.shared.data.repository.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// A simple base class for our ViewModels
abstract class ViewModel : KoinComponent {
    protected abstract val scope: CoroutineScope

    fun clear() {
        scope.cancel()
    }
}

class LoginViewModel : ViewModel() {

    private val authRepository: AuthRepository by inject()
    override val scope = CoroutineScope(Dispatchers.Main)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, errorMessage = null) }
    }

    fun login() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val request = LoginRequest(
                email = _uiState.value.email,
                password = _uiState.value.password
            )

            val result = authRepository.login(request)

            result.onSuccess { response ->
                if (response.token != null) {
                    // SUCCESS: We have a token
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccessToken = response.token
                        )
                    }
                } else {
                    // BUSINESS ERROR: Server returned an error message
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

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginSuccessToken: String? = null
)
