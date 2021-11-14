package com.haidev.coronavirusapp.di

import com.haidev.coronavirusapp.BuildConfig
import com.haidev.coronavirusapp.data.source.remote.provideApi
import com.haidev.coronavirusapp.data.source.remote.provideCacheInterceptor
import com.haidev.coronavirusapp.data.source.remote.provideHttpLoggingInterceptor
import com.haidev.coronavirusapp.data.source.remote.provideMoshiConverter
import org.koin.dsl.module

val remoteModule = module {
    single { provideCacheInterceptor() }
    single { provideHttpLoggingInterceptor() }
    single { provideMoshiConverter() }
    single {
        provideApi(
            BuildConfig.API_URL,
            get()
        )
    }
}
