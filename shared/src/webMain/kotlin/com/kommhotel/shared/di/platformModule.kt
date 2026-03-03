package com.kommhotel.shared.di

import com.kommhotel.shared.data.local.SessionManager
import org.koin.dsl.module

actual val platformModule = module {
    single { SessionManager() }
}
