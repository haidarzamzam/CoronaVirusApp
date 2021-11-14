package com.haidev.coronavirusapp.di

import com.haidev.coronavirusapp.ui.screen.main.HomeViewModel
import com.haidev.coronavirusapp.ui.screen.onboarding.OnboardingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { OnboardingViewModel(androidApplication()) }
    viewModel { HomeViewModel(androidApplication()) }
}
