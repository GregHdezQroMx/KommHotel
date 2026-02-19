package com.kommhotel.app.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.kommhotel.shared.presentation.login.LoginViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import kommhotel.shared.generated.resources.*

@Composable
fun LoginScreen(onLoginSuccess: (sessionId: String) -> Unit) {
    val viewModel: LoginViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.loginSuccessToken) {
        uiState.loginSuccessToken?.let {
            onLoginSuccess(it)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(Res.string.label_welcome))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text(stringResource(Res.string.label_email)) },
            isError = uiState.errorMessage != null
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text(stringResource(Res.string.label_password)) },
            visualTransformation = PasswordVisualTransformation(),
            isError = uiState.errorMessage != null
        )

        uiState.errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(onClick = { viewModel.login() }) {
                Text(stringResource(Res.string.button_login))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { /* TODO: Implement registration logic */ }) {
                Text(stringResource(Res.string.button_register))
            }
        }
    }
}