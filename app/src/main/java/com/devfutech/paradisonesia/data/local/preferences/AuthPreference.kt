package com.devfutech.paradisonesia.data.local.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named

class AuthPreference @Inject constructor(
    @Named("AuthDataPrefs") private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val TOKEN = "token"
    }

    fun getToken(): String = sharedPreferences.getString(TOKEN, "") ?: ""

    fun setToken(token: String) = sharedPreferences.edit().putString(TOKEN, token).apply()
}
