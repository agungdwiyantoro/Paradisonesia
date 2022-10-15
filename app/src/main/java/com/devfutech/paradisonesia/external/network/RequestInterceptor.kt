package com.devfutech.paradisonesia.external.network

import android.content.Context
import android.text.TextUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
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

            //val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiZDU3MDI2MjQ3MDdmNDE1ZmJmOGViYzY1YzNjNzQzNDA4MzkyMzllNGM5NWZkZGYyNjk0OGRlMjhmMzk2NzExYTk2MjkwMTc0ZDFiYzIzM2QiLCJpYXQiOjE2NjE1MTk3MjQsIm5iZiI6MTY2MTUxOTcyNCwiZXhwIjoxNjYxNTIzMzIzLCJzdWIiOiIyMCIsInNjb3BlcyI6W119.Peydox8P7kdKQ8DSZnQVikV3lG8albIQkaYZqdXoMjxduYat5uKqCTN2qNokwzhwpeF3REkzAK1cW_Pb4Kiklcj5v6bkVWLD8gO-IOcnKiTvSJ8dLQWNUWdznIhpu_o1Gwkh8gMir-RQg6T8_AsH_SyryyTE8I2V5T9FqjduHrpvtIldIvSJhTasVRquZQVp1O2670s_Mc_ovI6AjoFF02d9IHApVLqYnnKGnlm8lYQ8Kw8Ycbg6_X4E7tzRrpFnNZgSIuaXNT0XmTWu5JBSy_P1Lr6-u_MtYYjlW9avfFk52mKwH_MrPh6RqgOMeP2iOrzpSOEwh40z2-19IJbYXDgDheWDwU4lKrbR_WsA0eO8z1ulCC0_lQFopERw7MZ4KGm18jRRgDn1-kP-Tt-Ss692uPE3-F94AZpkOdn3fa-FjeIQV8biBA5Rbl2M3Nosv4JABvdeV7DqW2MbNtzpGHTKN8NDxSFtJ8zPy-0Jjd7Udbfd-DlqcgYyUY1McRoFyn4whJv9JDqVOSr__VF9i3GLRK3zXGUqIhGofj_iSR8Cwq_8GRY3di7ngR8VwBhuHdhO2fCqX7JF_3w3jMMPuIXWRdfgNBYI7i24IoYERriYSSHjg8ZXOOIScOrFBvAFJX3gy1KkdPj-F7xJO6QjoyuG4_r5GfcE6E9bVneMDXA"
            val originalRequest = chain.request()
            val authenticationRequest = setHeader(originalRequest,authPreference.getToken())
            val initialResponse = chain.proceed(authenticationRequest)
           //AccessToken.refreshCurrentAccessTokenAsync()
            Timber.tag("GGGXXX").d(authPreference.getToken())

            return when (initialResponse.code) {
                401 -> {
                    Timber.tag("GGGXXX").d("djancoek " + authPreference.getToken())

                    //AccessToken.refreshCurrentAccessTokenAsync()
                    //runBlocking {

                    //authPreference.viewModel.getTokenRefresh(authPreference.getToken())
                   // }
                   // initialResponse.close()
                   // setHeader(originalRequest, authPreference.getRefreshToken())

//                   userPreference.clearData()
//                    Firebase.auth.signOut()
//                    val intent = Intent(context, LoginActivity::class.java).apply {
//                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    }
//                    context.startActivity(intent)
                    //initialResponse.close()
                   // setHeader(chain.request(),authPreference.getToken())
                   // chain.proceed(authenticationRequest)
                    initialResponse
                }
                else -> initialResponse
            }
        }
    }

    private fun setHeader(request: Request, accessToken: String?): Request {
        val newRequest = request.newBuilder()

        Timber.tag("RequestInterceptor").d("SetHeader " + authPreference.getToken())
        if (!TextUtils.isEmpty(accessToken)) newRequest.addHeader("Authorization","Bearer $accessToken")

        return newRequest
            .header("Accept", "application/json, text/plain, */*")
            .header("Content-Type", "application/json")
            .header("platform","android")
            .build()
    }
}