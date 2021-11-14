package com.haidev.coronavirusapp.ui.screen.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.SingleValueDataSet
import com.anychart.graphics.vector.Fill
import com.anychart.graphics.vector.SolidFill
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

                initChartRatio(data[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun initChartRatio(data: CoronaStatisticModel.Response.Country) {
        val anyChartView = findViewById<AnyChartView>(R.id.chart_view_ratio)

        val totalCase = data.TotalConfirmed
        val totalAffected = data.TotalConfirmed.minus(data.TotalRecovered)
            .minus(data.TotalDeaths)
        val totalRecovered = data.TotalRecovered

        binding?.tvAffected?.text = ConverterUtils.convertToDecimal(totalAffected)
        binding?.tvRecovered?.text = ConverterUtils.convertToDecimal(totalRecovered)

        val percentAffected = (totalAffected.toDouble() / totalCase) * 100
        val percentRecovered = (totalRecovered.toDouble() / totalCase) * 100

        binding?.tvPercentRecovery?.text = "${percentRecovered.toInt()} %"
        val circularGauge = AnyChart.circular()
        circularGauge.data(
            SingleValueDataSet(
                arrayOf(
                    percentAffected.toInt().toString(),
                    percentRecovered.toInt().toString(),
                    "100"
                )
            )
        )
        circularGauge.fill("#fff")
            .stroke(null)
            .padding(0, 0, 0, 0)
            .margin(100, 100, 100, 25100)
        circularGauge.startAngle(180)
        circularGauge.sweepAngle(360)

        val xAxis = circularGauge.axis(0)
            .radius(100)
            .width(1)
            .fill(null as Fill?)
        xAxis.scale()
            .minimum(0)
            .maximum(100)
        xAxis.ticks("{ interval: 1 }")
            .minorTicks("{ interval: 1 }")
        xAxis.labels().enabled(false)
        xAxis.ticks().enabled(false)
        xAxis.minorTicks().enabled(false)

        val bar0 = circularGauge.bar(0)
        bar0.dataIndex(0)
        bar0.radius(100)
        bar0.width(8)
        bar0.fill(SolidFill("#01ECCD", 1))
        bar0.stroke(null)
        bar0.zIndex(5)
        val bar100 = circularGauge.bar(100)
        bar100.dataIndex(5)
        bar100.radius(100)
        bar100.width(8)
        bar100.fill(SolidFill("#F5F4F4", 1))
        bar100.stroke("1 #e5e4e4")
        bar100.zIndex(4)

        val bar1 = circularGauge.bar(1)
        bar1.dataIndex(1)
        bar1.radius(80)
        bar1.width(8)
        bar1.fill(SolidFill("#00BFA6", 1))
        bar1.stroke(null)
        bar1.zIndex(5)
        val bar101 = circularGauge.bar(101)
        bar101.dataIndex(5)
        bar101.radius(80)
        bar101.width(8)
        bar101.fill(SolidFill("#F5F4F4", 1))
        bar101.stroke("1 #e5e4e4")
        bar101.zIndex(4)

        val bar2 = circularGauge.bar(2)
        bar2.dataIndex(2)
        bar2.radius(60)
        bar2.width(8)
        bar2.fill(SolidFill("#E4E4E4", 1))
        bar2.stroke(null)
        bar2.zIndex(5)
        val bar102 = circularGauge.bar(102)
        bar102.dataIndex(5)
        bar102.radius(60)
        bar102.width(8)
        bar102.fill(SolidFill("#F5F4F4", 1))
        bar102.stroke("1 #e5e4e4")
        bar102.zIndex(4)

        circularGauge.margin(0, 0, 0, 0)

        anyChartView.setChart(circularGauge)
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
        binding?.tvTitleAffected?.text = getString(R.string.affected_n)
        binding?.tvTitleRecovered?.text = getString(R.string.recovered_n)
    }

    override fun setLayout() = R.layout.activity_home

    override fun getViewModels() = homeViewModel
}