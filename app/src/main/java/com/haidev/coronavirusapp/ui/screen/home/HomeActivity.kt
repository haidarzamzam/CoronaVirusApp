package com.haidev.coronavirusapp.ui.screen.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.haidev.coronavirusapp.R
import com.haidev.coronavirusapp.data.model.CoronaStatisticModel
import com.haidev.coronavirusapp.databinding.ActivityHomeBinding
import com.haidev.coronavirusapp.ui.base.BaseActivity
import com.haidev.coronavirusapp.util.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity :
    BaseActivity<ActivityHomeBinding, HomeViewModel>(),
    HomeNavigator {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding

    var dateUpdate: String = " - "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        homeViewModel.navigator = this
    }

    override fun onReadyAction() {
        super.onReadyAction()
        DialogLoading.displayLoadingWithText(this, "Please wait...", false)
        initSpinnerLanguage()
        homeViewModel.getCoronaStatisticsAsync()
    }

    override fun onObserveAction() {
        super.onObserveAction()
        with(homeViewModel) {
            observeActivity(coronaStatistics, ::handleCoronaStatistics)
        }
    }

    private fun handleCoronaStatistics(it: Resource<CoronaStatisticModel.Response>?) {
        when (it?.status) {
            Status.SUCCESS -> {
                DialogLoading.hideLoading()
                dateUpdate =
                    ConverterUtils.convertDatetime(it.data?.Global?.Date.toString()).toString()
                binding?.tvDate?.text = getString(R.string.last_update) + " " + dateUpdate

                it.data?.Countries?.let { item -> initSpinnerCountry(item) }

                val activeCase =
                    it.data?.Countries?.get(0)?.TotalConfirmed?.minus(it.data.Countries[0].TotalRecovered)
                        ?.minus(it.data.Countries[0].TotalDeaths)
                binding?.tvTotalCase?.text =
                    it.data?.Countries?.get(0)?.TotalConfirmed?.let { it1 ->
                        ConverterUtils.convertToDecimal(it1)
                    }
                binding?.tvActiveCase?.text =
                    activeCase?.let { it1 -> ConverterUtils.convertToDecimal(it1) }
                binding?.tvRecoveredCase?.text =
                    it.data?.Countries?.get(0)?.TotalRecovered?.let { it1 ->
                        ConverterUtils.convertToDecimal(it1)
                    }
                binding?.tvDeathCase?.text = it.data?.Countries?.get(0)?.TotalDeaths?.let { it1 ->
                    ConverterUtils.convertToDecimal(it1)
                }
            }
            Status.ERROR -> {
                DialogLoading.hideLoading()
            }
            else -> {
                DialogLoading.hideLoading()
            }
        }
    }

    private fun initSpinnerCountry(data: List<CoronaStatisticModel.Response.Country>) {
        val itemCountryAdapter =
            ItemCountryAdapter(this, data)
        binding?.spinnerCountry?.adapter = itemCountryAdapter
        binding?.spinnerCountry?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val activeCase =
                    ConverterUtils.convertToDecimal(
                        data[position].TotalConfirmed.minus(data[position].TotalRecovered)
                            .minus(data[position].TotalDeaths)
                    )
                binding?.tvTotalCase?.text =
                    ConverterUtils.convertToDecimal(data[position].TotalConfirmed)
                binding?.tvActiveCase?.text = activeCase
                binding?.tvRecoveredCase?.text =
                    ConverterUtils.convertToDecimal(data[position].TotalRecovered)
                binding?.tvDeathCase?.text =
                    ConverterUtils.convertToDecimal(data[position].TotalDeaths)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initSpinnerLanguage() {
        val itemLanguageAdapter =
            ItemLanguageAdapter(this, ItemLanguageData.generateItemLanguage())
        binding?.spinnerLanguage?.adapter = itemLanguageAdapter
        binding?.spinnerLanguage?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (ItemLanguageData.generateItemLanguage()[position].name == "EN") {
                    updateLanguage("en")
                } else {
                    updateLanguage("id")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding?.tvTitle?.text = getString(R.string.statistics)
        binding?.tvSelectCountry?.text = getString(R.string.select_country)
        binding?.tvDate?.text = getString(R.string.last_update) + " " + dateUpdate
        binding?.tvTitleTotalCase?.text = getString(R.string.total_case)
        binding?.tvTitleActiveCase?.text = getString(R.string.active_case)
        binding?.tvTitleRecoveredCase?.text = getString(R.string.recovered)
        binding?.tvTitleDeathCase?.text = getString(R.string.death)
        binding?.tvTitleRatioRecovery?.text = getString(R.string.ratio_of_nrecovery)
    }

    override fun setLayout() = R.layout.activity_home

    override fun getViewModels() = homeViewModel
}