package com.haidev.coronavirusapp.di

import com.haidev.coronavirusapp.data.source.repository.CoronaStatisticRepository
import com.haidev.coronavirusapp.ui.screen.home.HomeViewModel
import com.haidev.coronavirusapp.ui.screen.onboarding.OnboardingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { OnboardingViewModel(androidApplication()) }
    viewModel { HomeViewModel(get(), androidApplication()) }
}

val apiRepositoryModule = module {
    single { CoronaStatisticRepository(get()) }
}
