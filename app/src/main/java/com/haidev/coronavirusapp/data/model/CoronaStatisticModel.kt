package com.haidev.coronavirusapp.data.model

object CoronaStatisticModel {
    data class Response(
        val Countries: List<Country>,
        val Date: String,
        val Global: GlobalData,
        val ID: String,
        val Message: String
    ) {
        data class Country(
            val Country: String,
            val CountryCode: String,
            val Date: String,
            val ID: String,
            val NewConfirmed: Int,
            val NewDeaths: Int,
            val NewRecovered: Int,
            val Premium: Any,
            val Slug: String,
            val TotalConfirmed: Int,
            val TotalDeaths: Int,
            val TotalRecovered: Int
        )

        data class GlobalData(
            val Date: String,
            val NewConfirmed: Int,
            val NewDeaths: Int,
            val NewRecovered: Int,
            val TotalConfirmed: Int,
            val TotalDeaths: Int,
            val TotalRecovered: Int
        )

        class Premium
    }


}