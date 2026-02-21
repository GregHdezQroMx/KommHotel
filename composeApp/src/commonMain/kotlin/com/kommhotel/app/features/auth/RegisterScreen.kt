package com.kommhotel.app.features.auth

import androidx.compose.foundation.layout.*
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
import com.kommhotel.shared.presentation.register.RegisterViewModel
import kommhotel.shared.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit) {
    val viewModel: RegisterViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.registrationSuccess) {
        if (uiState.registrationSuccess) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(Res.string.label_register_title))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = { viewModel.onFirstNameChange(it) },
            label = { Text(stringResource(Res.string.label_first_name)) },
            isError = uiState.errorMessage != null
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = uiState.lastName,
            onValueChange = { viewModel.onLastNameChange(it) },
            label = { Text(stringResource(Res.string.label_last_name)) },
            isError = uiState.errorMessage != null
        )

        Spacer(modifier = Modifier.height(8.dp))

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
            Button(onClick = { viewModel.register() }) {
                Text(stringResource(Res.string.button_register))
            }
        }
    }
}
