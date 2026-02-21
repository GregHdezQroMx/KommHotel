package com.kommhotel.shared.di

import com.kommhotel.shared.data.repository.AuthRepository
import com.kommhotel.shared.data.repository.AuthRepositoryImpl
import com.kommhotel.shared.data.repository.BookingRepository
import com.kommhotel.shared.data.repository.BookingRepositoryImpl
import com.kommhotel.shared.data.repository.RoomRepository
import com.kommhotel.shared.data.repository.RoomRepositoryImpl
import com.kommhotel.shared.presentation.home.HomeViewModel
import com.kommhotel.shared.presentation.login.LoginViewModel
import com.kommhotel.shared.presentation.register.RegisterViewModel
import com.kommhotel.shared.presentation.room_detail.RoomDetailViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(
            networkModule,
            repositoryModule,
            viewModelModule
        )
    }
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RoomRepository> { RoomRepositoryImpl(get()) }
    single<BookingRepository> { BookingRepositoryImpl(get()) } // Added BookingRepository
}

val viewModelModule = module {
    factoryOf(::LoginViewModel)
    factoryOf(::RegisterViewModel)
    factoryOf(::HomeViewModel)
    factory { (roomId: String) -> RoomDetailViewModel(roomId) } // Definition for ViewModel with parameters
}
