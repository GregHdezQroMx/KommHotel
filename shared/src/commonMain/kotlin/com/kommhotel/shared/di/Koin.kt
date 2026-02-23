package com.kommhotel.shared.di

import com.kommhotel.shared.data.local.SessionManager
import com.kommhotel.shared.data.repository.AuthRepository
import com.kommhotel.shared.data.repository.AuthRepositoryImpl
import com.kommhotel.shared.data.repository.BookingRepository
import com.kommhotel.shared.data.repository.BookingRepositoryImpl
import com.kommhotel.shared.data.repository.RoomRepository
import com.kommhotel.shared.data.repository.RoomRepositoryImpl
import com.kommhotel.shared.presentation.bookings.MyBookingsViewModel
import com.kommhotel.shared.presentation.home.HomeViewModel
import com.kommhotel.shared.presentation.login.LoginViewModel
import com.kommhotel.shared.presentation.register.RegisterViewModel
import com.kommhotel.shared.presentation.room_detail.RoomDetailViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val platformModule: Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            networkModule,
            repositoryModule,
            viewModelModule,
            platformModule
        )
    }
}

// HttpClient now requires SessionManager to be injected
val networkModule = module {
    single {
        val sessionManager: SessionManager = get()
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            // Install the Auth plugin
            install(Auth) {
                bearer {
                    loadTokens {
                        val token = sessionManager.getToken().first()
                        if (token != null) {
                            BearerTokens(accessToken = token, refreshToken = "")
                        } else {
                            null
                        }
                    }
                    // ADDED: Force sending the token on the first request
                    sendWithoutRequest { request ->
                        // You can add logic here to only send the token to specific domains
                        true // Send for all requests
                    }
                }
            }
        }
    }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RoomRepository> { RoomRepositoryImpl(get()) }
    single<BookingRepository> { BookingRepositoryImpl(get()) }
}

val viewModelModule = module {
    factoryOf(::LoginViewModel)
    factoryOf(::RegisterViewModel)
    factoryOf(::HomeViewModel)
    factory { (roomId: String) -> RoomDetailViewModel(roomId) }
    factoryOf(::MyBookingsViewModel)
}
