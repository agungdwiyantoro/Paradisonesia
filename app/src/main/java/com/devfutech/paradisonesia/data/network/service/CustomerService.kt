package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.customer.CustomerAuthResponse
import com.devfutech.paradisonesia.domain.model.user.Customer
import retrofit2.http.*

/**
 * Created by devfutech on 9/10/2021.
 */
interface CustomerService {
    @GET("profiles")
    suspend fun customerProfile(): BaseResponse<Customer>

    @POST("auth")
    //@FormUrlEncoded
    suspend fun authCustomer(
        @Body payload: Map<String,String>
    ): BaseResponse<CustomerAuthResponse>
}