package com.devfutech.paradisonesia.external.network

import android.content.Context
import android.text.TextUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.data.local.preferences.SessionManager
import com.devfutech.paradisonesia.domain.usecase.RefreshTokenUseCase
import com.devfutech.paradisonesia.external.Resource
import com.devfutech.paradisonesia.presentation.base.BaseFragment
import com.devfutech.paradisonesia.presentation.fragments.edit_profile.EditProfileViewModel
import com.devfutech.paradisonesia.presentation.fragments.refresh_token.RefreshTokenViewModel
import com.facebook.AccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * Created by devfutech on 9/10/2021.
 */
class RequestInterceptor @Inject constructor(
   private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var accessToken = sessionManager.getAccessToken()

        val response = chain.proceed(newRequestWithAccessToken(accessToken, request))

        Timber.tag("REQUEST INTERCEPTOR").d("PRE INTERCEPT" + accessToken)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            //val newAccessToken = sessionManager.getAccessToken()
          /*
            val newAccessToken = refreshToken()
            if (newAccessToken != accessToken) {
                response.close()
                return chain.proceed(newRequestWithAccessToken(accessToken, request))
            } else {
                accessToken = refreshToken()
                Timber.tag("REQUEST INTERCEPTOR").d("POST INTERCEPT" + accessToken)
                if (accessToken.isNullOrBlank()) {
                    sessionManager.logout()
                    Timber.tag("REQUEST INTERCEPTOR").d("accessTokenIsNull")
                    Timber.tag("REQUEST INTERCEPTORx").d("ACCESSTOKENISNULL")
                    return response
                }
                return chain.proceed(newRequestWithAccessToken(accessToken, request))
            }

           */
        }

        return response
    }

    private fun newRequestWithAccessToken(accessToken: String?, request: Request): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

    private fun refreshToken(): String? {
        synchronized(this) {
            val refreshToken = sessionManager.getRefreshToken()
            refreshToken?.let {
                return sessionManager.refreshToken(refreshToken)
            } ?: return null
        }
    }


}