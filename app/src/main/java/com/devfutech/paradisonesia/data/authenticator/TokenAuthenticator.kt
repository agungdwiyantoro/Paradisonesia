package com.devfutech.paradisonesia.data.authenticator

import android.content.Context
import com.devfutech.paradisonesia.data.network.response.RefreshToken.TokenRefreshResponse
import com.devfutech.paradisonesia.external.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    context: Context,
    private val tokenApi: TokenRefreshApi
) : Authenticator, BaseRepository(tokenApi) {

    private val appContext = context.applicationContext
    private val userPreferences = UserPreferences(appContext)

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val tokenResponse = getUpdatedToken()) {
                is Resource.Success -> {
                    userPreferences.saveAccessTokens(
                        tokenResponse.value.access_token!!,
                        tokenResponse.value.refresh_token!!
                    )
                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokenResponse.value.access_token}")
                        .build()
                }
                else -> null
            }
        }
    }

    private suspend fun getUpdatedToken(): Resource<TokenRefreshResponse> {
        val refreshToken = userPreferences.refreshToken.first()
        return safeApiCall { tokenApi.refreshAccessToken(refreshToken) }
    }
}