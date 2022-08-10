package com.devfutech.paradisonesia.data.network.response.customer


import com.devfutech.paradisonesia.domain.model.user.Customer
import com.google.gson.annotations.SerializedName

data class CustomerAuthResponse(
    @SerializedName("api_token")
    val apiToken: String?,
    @SerializedName("user")
    val user: UserCustomerResponse?,
    @SerializedName("user_uid")
    val userUid: Boolean?
){
    fun toCustomer() = Customer(
        customerLevelId = user?.customerLevelId,
        email = user?.email,
        id = user?.id,
        merchantId = user?.merchantId,
        name = user?.name,
        phone = user?.phone,
        statusId = user?.statusId,
        apiToken = apiToken
    )
}