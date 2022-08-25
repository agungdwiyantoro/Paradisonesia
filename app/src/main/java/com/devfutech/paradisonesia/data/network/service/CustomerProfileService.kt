package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.customer.CustomerAuthResponse
import com.devfutech.paradisonesia.data.network.response.customer.UserCustomerResponse
import com.devfutech.paradisonesia.data.network.response.customer.UserProfileCustomerResponse
import com.google.android.datatransport.runtime.EncodedPayload
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface CustomerProfileService {
    @POST("profile")
    @FormUrlEncoded
    suspend fun CustomerProfile(
        @FieldMap payload: Map<String,String>
    ): BaseResponse<CustomerAuthResponse>
}