package com.haidev.coronavirusapp.data.source.repository

import com.haidev.coronavirusapp.data.model.CoronaStatisticModel
import com.haidev.coronavirusapp.data.source.endpoint.ApiService

class CoronaStatisticRepository(private val apiService: ApiService) {
    suspend fun getCoronaStatistic(
    ): CoronaStatisticModel.Response {
        return apiService.getCoronaStatistic().await()
    }
}