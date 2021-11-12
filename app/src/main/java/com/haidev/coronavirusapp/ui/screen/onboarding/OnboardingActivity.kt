package com.haidev.coronavirusapp.ui.screen.onboarding

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.haidev.coronavirusapp.BuildConfig
import com.haidev.coronavirusapp.R
import com.haidev.coronavirusapp.data.sharedpreferences.SharedPreference
import com.haidev.coronavirusapp.databinding.ActivityOnboardingBinding
import com.haidev.coronavirusapp.ui.base.BaseActivity
import com.haidev.coronavirusapp.ui.screen.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingActivity :
    BaseActivity<ActivityOnboardingBinding, OnboardingViewModel>(),
    OnboardingNavigator {

    private val onboardingViewModel: OnboardingViewModel by viewModel()
    private var _binding: ActivityOnboardingBinding? = null
    private val binding get() = _binding

    private lateinit var onboardingAdapter: OnboardingAdapter
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val reqCode: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewDataBinding()
        binding?.lifecycleOwner = this
        onboardingViewModel.navigator = this

        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.FIREBASE_WEB_CLIENT)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance()
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

        binding?.buttonLoginGoogle?.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, reqCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqCode) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            SharedPreference.setEmail(this, account.email.toString())
                            SharedPreference.setUsername(this, account.displayName.toString())
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                Log.d("ERRORBOSSS : ", e.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    override fun setLayout() = R.layout.activity_onboarding

    override fun getViewModels() = onboardingViewModel
}