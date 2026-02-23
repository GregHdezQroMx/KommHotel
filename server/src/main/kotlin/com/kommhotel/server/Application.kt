package com.kommhotel.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kommhotel.server.routes.authRoutes
import com.kommhotel.server.routes.bookingRoutes
import com.kommhotel.server.routes.roomRoutes
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    // Hardcoded values for the JWT token
    val secret = "my-super-secret-for-jwt"
    val issuer = "http://0.0.0.0:8080"
    val audience = "users"

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization) // <-- Allow Authorization header
        anyHost()
    }

    // Install and configure JWT authentication
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "KommHotel"
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .withAudience(audience)
                .withIssuer(issuer)
                .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }

    routing {
        get("/health") {
            call.respondText("Server is healthy!")
        }

        authRoutes()
        roomRoutes()
        bookingRoutes()
    }
}
