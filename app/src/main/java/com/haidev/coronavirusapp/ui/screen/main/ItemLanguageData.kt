package com.haidev.coronavirusapp.ui.screen.main

object ItemLanguageData {
    fun generateItemLanguage(): List<ItemLanguageModel> {
        val listItemLanguage: ArrayList<ItemLanguageModel> = arrayListOf()
        listItemLanguage.add(
            ItemLanguageModel(
                "ID",
                "ic_local_id"
            )
        )
        listItemLanguage.add(
            ItemLanguageModel(
                "EN",
                "ic_local_en"
            )
        )

        return listItemLanguage
    }
}