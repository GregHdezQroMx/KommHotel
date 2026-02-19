package com.kommhotel.shared.di

import com.kommhotel.shared.data.repository.AuthRepository
import com.kommhotel.shared.data.repository.AuthRepositoryImpl
import com.kommhotel.shared.presentation.login.LoginViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

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
}

val viewModelModule = module {
    factoryOf(::LoginViewModel)
}
