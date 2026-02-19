package com.kommhotel.app.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kommhotel.shared.generated.resources.Res
import kommhotel.shared.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

/**
 * A simple, stateless splash screen that uses string resources.
 */
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(Res.string.app_name))
    }
}