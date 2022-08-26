package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.customer.CustomerProfileAuthResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST


interface CustomerProfileService {
    @POST("profiles")
    @FormUrlEncoded
    suspend fun CustomerProfile(
        @FieldMap payload: Map<String,String>
    ): BaseResponse<CustomerProfileAuthResponse>
}