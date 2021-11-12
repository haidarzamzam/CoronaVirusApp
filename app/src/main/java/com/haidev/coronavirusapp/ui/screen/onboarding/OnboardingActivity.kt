package com.haidev.coronavirusapp.ui.screen.onboarding

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.haidev.coronavirusapp.R
import com.haidev.coronavirusapp.databinding.ActivityOnboardingBinding
import com.haidev.coronavirusapp.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity :
    BaseActivity<ActivityOnboardingBinding, OnboardingViewModel>(),
    OnboardingNavigator {

    private val onboardingViewModel: OnboardingViewModel by viewModel()
    private var _binding: ActivityOnboardingBinding? = null
    private val binding get() = _binding

    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        onboardingViewModel.navigator = this
    }

    override fun onReadyAction() {
        super.onReadyAction()
        initView()
        initSliderOnboarding()
    }

    private fun initView() {
        val text = resources.getString(R.string.onboarding_header)
        binding?.tvTitle?.text = SpannableStringBuilder(text).apply {
            setSpan(
                ForegroundColorSpan(resources.getColor(R.color.payne_grey)),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(resources.getColor(R.color.carribean_green)),
                6,
                11,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun initSliderOnboarding() {
        onboardingAdapter = OnboardingAdapter(this, ItemOnboardingData.generateItemOnboarding())
        binding?.viewPager?.adapter = onboardingAdapter
        binding?.viewPager?.let { binding?.dotsIndicator?.setViewPager(it) }
        binding?.viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    binding?.buttonNext?.visibility = View.INVISIBLE
                    binding?.buttonLoginGoogle?.visibility = View.VISIBLE
                } else {
                    binding?.buttonNext?.visibility = View.VISIBLE
                    binding?.buttonLoginGoogle?.visibility = View.INVISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding?.buttonNext?.setOnClickListener {
            binding?.viewPager?.currentItem?.plus(1)?.let { it ->
                binding?.viewPager?.setCurrentItem(
                    it, true
                )
            }
        }
    }

    override fun setLayout() = R.layout.activity_onboarding

    override fun getViewModels() = onboardingViewModel
}