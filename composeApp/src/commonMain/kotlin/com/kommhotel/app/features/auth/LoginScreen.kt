package com.kommhotel.app.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kommhotel.shared.generated.resources.Res
import kommhotel.shared.generated.resources.button_login
import kommhotel.shared.generated.resources.button_register
import kommhotel.shared.generated.resources.label_email
import kommhotel.shared.generated.resources.label_password
import kommhotel.shared.generated.resources.label_welcome
import org.jetbrains.compose.resources.stringResource

/**
 * Displays the login/registration screen.
 * @param onLoginSuccess Callback invoked when the user successfully logs in, providing a session ID.
 */
@Composable
fun LoginScreen(onLoginSuccess: (sessionId: String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(stringResource(Res.string.label_welcome))

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(Res.string.label_email)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(Res.string.label_password)) },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onLoginSuccess("fake-session-id-12345")
        }) {
            Text(stringResource(Res.string.button_login))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { /* TODO: Implement registration logic */ }) {
            Text(stringResource(Res.string.button_register))
        }
    }
}