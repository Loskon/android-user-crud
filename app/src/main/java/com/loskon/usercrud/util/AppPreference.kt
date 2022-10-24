package com.loskon.usercrud.util

import android.content.Context
import androidx.preference.PreferenceManager

object AppPreference {

    private const val PREF_KEY_AUTHENTICATED = "PREF_KEY_AUTHENTICATED"

    fun set(context: Context, key: String, value: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean(key, value).apply()
    }

    fun get(context: Context, key: String, default: Boolean = false): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, default)
    }

    fun setUserAuthenticated(context: Context, authenticated: Boolean) {
        set(context, PREF_KEY_AUTHENTICATED, authenticated)
    }

    fun isUserAuthenticated(context: Context): Boolean {
        return get(context, PREF_KEY_AUTHENTICATED)
    }
}