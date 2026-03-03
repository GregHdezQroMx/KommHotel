package com.kommhotel.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.kommhotel.app.features.admin.AdminDashboard
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val body = document.body
    if (body != null) {
        ComposeViewport(body) {
            AdminDashboard()
        }
    }
}
