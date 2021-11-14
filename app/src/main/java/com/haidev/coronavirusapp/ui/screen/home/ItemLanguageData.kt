package com.haidev.coronavirusapp.ui.screen.home

object ItemLanguageData {
    fun generateItemLanguage(): List<ItemLanguageModel> {
        val listItemLanguage: ArrayList<ItemLanguageModel> = arrayListOf()
        listItemLanguage.add(
            ItemLanguageModel(
                "EN",
                "ic_local_en"
            )
        )
        listItemLanguage.add(
            ItemLanguageModel(
                "ID",
                "ic_local_id"
            )
        )
        return listItemLanguage
    }
}