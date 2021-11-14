package com.haidev.coronavirusapp.data.source.endpoint

import com.haidev.coronavirusapp.data.model.CoronaStatisticModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ApiService {
    @GET("summary")
    fun getCoronaStatistic(): Deferred<CoronaStatisticModel.Response>
}