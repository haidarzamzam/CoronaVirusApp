package com.haidev.coronavirusapp.ui.screen.onboarding

import com.haidev.coronavirusapp.R

object ItemOnboardingData {
    fun generateItemOnboarding(): List<ItemOnboardingModel> {
        val listItemOnboarding: ArrayList<ItemOnboardingModel> = arrayListOf()
        listItemOnboarding.add(
            ItemOnboardingModel(
                R.string.onboarding_title_1,
                R.string.onboarding_desc,
                R.drawable.img_onboarding_1
            )
        )
        listItemOnboarding.add(
            ItemOnboardingModel(
                R.string.onboarding_title_2,
                R.string.onboarding_desc,
                R.drawable.img_onboarding_2
            )
        )
        listItemOnboarding.add(
            ItemOnboardingModel(
                R.string.onboarding_title_3,
                R.string.onboarding_desc,
                R.drawable.img_onboarding_3
            )
        )

        return listItemOnboarding
    }
}