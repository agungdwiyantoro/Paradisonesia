package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import retrofit2.http.POST

interface LogoutService {
    @POST("logout")
    suspend fun authLogout(): BaseResponse<Unit>
}