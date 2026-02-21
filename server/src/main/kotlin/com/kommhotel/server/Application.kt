package com.kommhotel.server

import com.kommhotel.server.routes.authRoutes
import com.kommhotel.server.routes.bookingRoutes
import com.kommhotel.server.routes.roomRoutes
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.*
import io.ktor.server.routing.*

// Simplified for HTTP-only debug environment
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/health") {
            call.respondText("Server is healthy!")
        }

        authRoutes()
        roomRoutes()
        bookingRoutes() // <-- ACTIVATED
    }
}
