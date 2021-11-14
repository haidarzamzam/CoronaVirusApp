package com.haidev.coronavirusapp.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedPreference {

    const val EMAIL = "email"
    const val USERNAME = "username"
    const val LOCALE = "locale"

    private fun getSharedPreference(ctx: Context?): SharedPreferences? {
        return PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    private fun editor(context: Context, const: String, string: String) {
        getSharedPreference(
            context
        )?.edit()?.putString(const, string)?.apply()
    }

    fun getEmail(context: Context) = getSharedPreference(
        context
    )?.getString(EMAIL, "")

    fun setEmail(context: Context, email: String) {
        editor(
            context,
            EMAIL,
            email
        )
    }

    fun getUsername(context: Context) = getSharedPreference(
        context
    )?.getString(USERNAME, "")

    fun setUsername(context: Context, username: String) {
        editor(
            context,
            USERNAME,
            username
        )
    }

    fun getLocale(context: Context) = getSharedPreference(
        context
    )?.getString(LOCALE, "")

    fun setLocale(context: Context, username: String) {
        editor(
            context,
            LOCALE,
            username
        )
    }
}