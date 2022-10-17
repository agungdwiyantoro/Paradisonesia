package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.customer.CustomerAuthResponse
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerGetDetail
import retrofit2.http.*

/**
 * Created by devfutech on 9/10/2021.
 */
interface CustomerService {
    @GET("profiles")
    suspend fun customerProfile(): BaseResponse<CustomerGetDetail>

    @POST("profiles")
    @FormUrlEncoded
    suspend fun updateCustomerProfile(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("gender") gender: Int,
        @Field("birth_date") birth_date: String,
    ): BaseResponse<CustomerGetDetail.ProfileData?>

    @POST("auth")
    @FormUrlEncoded
    suspend fun authCustomer(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("user_uid") user_uid: String,
        @Field("player_id") player_id: String,
        @Field("is_email_verivied") is_email_verivied: String
    ): BaseResponse<CustomerAuthResponse>
}