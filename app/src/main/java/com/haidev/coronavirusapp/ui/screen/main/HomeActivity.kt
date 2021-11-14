package com.haidev.coronavirusapp.ui.screen.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.haidev.coronavirusapp.R
import com.haidev.coronavirusapp.data.model.CoronaStatisticModel
import com.haidev.coronavirusapp.databinding.ActivityHomeBinding
import com.haidev.coronavirusapp.ui.base.BaseActivity
import com.haidev.coronavirusapp.util.DialogLoading
import com.haidev.coronavirusapp.util.Resource
import com.haidev.coronavirusapp.util.Status
import com.haidev.coronavirusapp.util.observeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity :
    BaseActivity<ActivityHomeBinding, HomeViewModel>(),
    HomeNavigator {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        homeViewModel.navigator = this
    }

    override fun onReadyAction() {
        super.onReadyAction()
        DialogLoading.displayLoadingWithText(this, "Please wait...", false)
        initSpinner()
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
            Status.LOADING -> {

            }
            Status.SUCCESS -> {
                DialogLoading.hideLoading()
                Toast.makeText(
                    this,
                    it.data?.Countries?.get(0)?.NewConfirmed.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            Status.ERROR -> {
                DialogLoading.hideLoading()
            }
            else -> {
                DialogLoading.hideLoading()
            }
        }
    }

    private fun initSpinner() {
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
                // another interface callback
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding?.tvTitle?.text = getString(R.string.statistics)
        binding?.tvSelectCountry?.text = getString(R.string.select_country)
        binding?.tvDate?.text = getString(R.string.last_update)
    }

    override fun setLayout() = R.layout.activity_home

    override fun getViewModels() = homeViewModel
}