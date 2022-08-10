package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.customer.CustomerAuthResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by devfutech on 9/10/2021.
 */
interface CustomerService {
    @POST("auth")
    @FormUrlEncoded
    suspend fun authCustomer(
        @FieldMap payload: Map<String,String>
    ): BaseResponse<CustomerAuthResponse>
}