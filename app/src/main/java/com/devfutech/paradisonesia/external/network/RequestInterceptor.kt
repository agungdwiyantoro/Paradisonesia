package com.devfutech.paradisonesia.external.network

import android.content.Context
import android.text.TextUtils
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by devfutech on 9/10/2021.
 */
class RequestInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authPreference: AuthPreference
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val originalRequest = chain.request()
            val authenticationRequest = setHeader(originalRequest, authPreference.getToken())
            val initialResponse = chain.proceed(authenticationRequest)

            return when (initialResponse.code) {
                401 -> {
//                    userPreference.clearData()
//                    Firebase.auth.signOut()
//                    val intent = Intent(context, LoginActivity::class.java).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    }
//                    context.startActivity(intent)
                    initialResponse
                }
                else -> initialResponse
            }
        }
    }

    private fun setHeader(request: Request, accesToken: String?): Request {
        val newRequest = request.newBuilder()

        if (!TextUtils.isEmpty(accesToken)) newRequest.addHeader("Authorization","Bearer $accesToken")

        return newRequest
            .header("Accept", "application/json, text/plain, */*")
            .header("Content-Type", "application/json")
            .header("platform","android")
            .build()
    }
}