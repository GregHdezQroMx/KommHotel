package com.kommhotel.shared.presentation.login

import com.kommhotel.shared.data.local.SessionManager
import com.kommhotel.shared.data.repository.AuthRepository
import com.kommhotel.shared.data.repository.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginSuccessToken: String? = null
)

// Base ViewModel now implements KoinComponent
open class ViewModel : KoinComponent {
    open val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
}

class LoginViewModel : ViewModel() {

    private val authRepository: AuthRepository by inject()
    private val sessionManager: SessionManager by inject()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(text: String) {
        _uiState.update { it.copy(email = text, errorMessage = null) }
    }

    fun onPasswordChange(text: String) {
        _uiState.update { it.copy(password = text, errorMessage = null) }
    }

    fun login() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val request = LoginRequest(email = _uiState.value.email, password = _uiState.value.password)
            val result = authRepository.login(request)

            result.onSuccess { response ->
                if (response.token != null) {
                    sessionManager.saveToken(response.token)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            loginSuccessToken = response.token
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = response.error ?: "An unknown server error occurred"
                        )
                    }
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