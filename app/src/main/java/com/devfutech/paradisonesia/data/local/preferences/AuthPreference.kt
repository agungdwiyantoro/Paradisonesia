package com.devfutech.paradisonesia.data.local.preferences

import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.fragments.product.ProductViewModel
import com.devfutech.paradisonesia.presentation.fragments.refresh_token.RefreshTokenViewModel
import javax.inject.Inject
import javax.inject.Named

class AuthPreference @Inject constructor(
   private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val SHARED_PREFS = "APP_SHARED_PREFS"
        const val TOKEN = "TOKEN"
        const val REFRESH_TOKEN = "REFRESH_TOKEN"
    }

    fun getToken(): String? = sharedPreferences.getString(TOKEN, null)

    fun setToken(token: String) = sharedPreferences.edit().putString(TOKEN, token).apply()

    fun getRefreshToken(): String? = sharedPreferences.getString(REFRESH_TOKEN, null)

    fun setRefreshToken(token_refresh: String)= sharedPreferences.edit().putString(REFRESH_TOKEN, token_refresh).apply()

}
