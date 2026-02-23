package com.kommhotel.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // onWasmReady es necesario para inicializar el motor gráfico Skia en JS
    onWasmReady {
        val body = document.body
        if (body != null) {
            ComposeViewport(body) {
                App() // Aquí tu pantalla inicial
            }
        }
    }
}

