package com.devfutech.paradisonesia.external.network

import android.content.Context
import android.text.TextUtils
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.domain.savedPreference.SavedPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
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
            val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiZmU1MzlkMTVkMzQwNGZhYWY5NTg5MDgzYzY5NmRhMTE4MDE1NzE3YTIzYTgzMWQzMWIwZTg1NGVmMzBmY2VkNDdiNzc2NTc5MzA0OGNmMzAiLCJpYXQiOjE2NjE0OTM0MzMsIm5iZiI6MTY2MTQ5MzQzMywiZXhwIjoxNjYxNDk3MDMzLCJzdWIiOiIyMCIsInNjb3BlcyI6W119.RhcgXvnEmevh38DYPSNS8Go3KC8IuSqTR-_i-hLf-sHbJ5fIESSkanH2FVJE0CGyxq7U9_aM4iWfw6t8-kBkJiWk_ulfEeDy9p6BVGnAlOZknEvzrCtrwYY_U71HAw1-B6lX5JF7pl7R4uiKf0Fdc86ubMci5R5-PXaVJ5A1DziPtlhzOAF-hdoS19Rzxvrax2qKwHk7nN75R3vXWWpwqPEJfnOqATmGj0FFAnRWD8iODK0aUg0aVLm5nL6o5I9Og0Nl2lcSRv5G4yczgbx9c5qBIKeldQLgoJWObh-pAVXUh-h5Cm-uuCxa1iKGgvof47YAlFIdwyALb6rbodRvFI9i5FAsq3WbX_AANwmIgMyCBdCt4SE2WFWQUwdC1JUxGDOpklBGWnS_-v8VsUNbXekUR2sq8tQ1fj7BSrNBggWi543YVbc0kTrVTgvnBkOUV642zS5_Q28NMlSoMB2ps5UAl0UZ5PgW7KTsNk1sdrMqMCUxHbS5QktNF_a5cyyDeu14E6pC699sYvFcvmT5CcJdCRFNzTTtP5yOiHJkTMrQwlsSa7jCwMe3QLg1e2_skG5D7153oSVMs4MsadGjgioQ_E7NDh3UWa0YtFvzF2EyNa33Oj2c4eER4d7D93oIR06w_Qr5eixTGF_hF4zoFmp_fHBsHXwxW-MgKtKs3co"
            val originalRequest = chain.request()
            val authenticationRequest = setHeader(originalRequest, SavedPreference.getToken(context))//token)authPreference.getToken())
            val initialResponse = chain.proceed(authenticationRequest)

            Timber.tag("GGGXXX").d(SavedPreference.getToken(context))

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