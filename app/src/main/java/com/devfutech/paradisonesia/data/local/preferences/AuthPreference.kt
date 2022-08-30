package com.devfutech.paradisonesia.data.local.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named

class AuthPreference @Inject constructor(
    @Named("AuthDataPrefs") private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val TOKEN = "token"
        const val REFRESH_TOKEN = "refresh_token"
    }

    fun getToken(): String = sharedPreferences.getString(TOKEN, "") ?: ""

    fun setToken(token: String) = sharedPreferences.edit().putString(TOKEN, token).apply()

    fun getRefreshToken(): String = sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""

    fun setRefreshToken(token_refresh: String)= sharedPreferences.edit().putString(REFRESH_TOKEN, token_refresh).apply()
}
