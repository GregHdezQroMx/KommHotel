package com.kommhotel.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady
import com.kommhotel.app.features.admin.AdminDashboard

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        val body = document.body
        if (body != null) {
            ComposeViewport(body) {
                AdminDashboard() // Cargamos el Dashboard para el target JS también
            }
        }
    }
}
