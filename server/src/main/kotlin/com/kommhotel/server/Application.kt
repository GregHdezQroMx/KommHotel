package com.kommhotel.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kommhotel.server.db.DatabaseFactory
import com.kommhotel.server.repository.BookingRepositoryImpl
import com.kommhotel.server.repository.UserRepositoryImpl
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
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    val userRepository = UserRepositoryImpl()
    val bookingRepository = BookingRepositoryImpl()

    // Configure Content Negotiation to be more robust, matching the client's setup
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }

    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtConfig.realm
            verifier(JWT
                .require(Algorithm.HMAC256(JwtConfig.secret))
                .withAudience(JwtConfig.audience)
                .withIssuer(JwtConfig.issuer)
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

        authRoutes(userRepository)
        roomRoutes()
        bookingRoutes(userRepository, bookingRepository)
    }
}
