package com.kommhotel.app

import androidx.compose.ui.window.application
import androidx.compose.ui.window.Window

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KommHotel") {
        App()
    }
}