package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.RefreshToken.TokenRefreshResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenRefreshService {
    @POST("auth/refresh")
    @FormUrlEncoded
    suspend fun authRefreshToken(
        @Field("refresh_token") refreshToken: String?
    ): TokenRefreshResponse
}