package com.haidev.coronavirusapp.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> Fragment.observeFragment(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(viewLifecycleOwner, { action.invoke(it) })
}

fun <T> AppCompatActivity.observeActivity(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, { action.invoke(it) })
}