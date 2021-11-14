package com.haidev.coronavirusapp.ui.screen.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.haidev.coronavirusapp.data.model.CoronaStatisticModel
import com.haidev.coronavirusapp.data.source.repository.CoronaStatisticRepository
import com.haidev.coronavirusapp.ui.base.BaseViewModel
import com.haidev.coronavirusapp.util.ErrorUtils
import com.haidev.coronavirusapp.util.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CoronaStatisticRepository,
    application: Application
) : BaseViewModel<HomeNavigator>(application) {
    private val _coronaStatistics = MutableLiveData<Resource<CoronaStatisticModel.Response>>()
    val coronaStatistics: MutableLiveData<Resource<CoronaStatisticModel.Response>>
        get() = _coronaStatistics

    fun getCoronaStatisticsAsync() {
        viewModelScope.launch {
            _coronaStatistics.postValue(Resource.loading(null))
            try {
                val response = repository.getCoronaStatistic()
                _coronaStatistics.postValue(Resource.success(response))
            } catch (t: Throwable) {
                _coronaStatistics.postValue(
                    Resource.error(
                        ErrorUtils.getErrorThrowableMsg(t),
                        null,
                        t
                    )
                )
            }
        }
    }
}