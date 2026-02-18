package com.kommhotel.app.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kommhotel.app.generated.resources.Res
import com.kommhotel.app.generated.resources.app_name
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
        // This text is now loaded from string resources, enabling localization.
        Text(stringResource(Res.string.app_name))
    }
}