package com.devfutech.paradisonesia.data.network.response.customer


import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import com.google.gson.annotations.SerializedName

data class CustomerProfileAuthResponse(
    @SerializedName("api_token")
    val apiToken: String?,
    @SerializedName("user")
    val user: UserProfileCustomerResponse?,
    @SerializedName("user_uid")
    val userUid: Boolean?
){
    fun toCustomerProfile() = CustomerProfile(
        name = user?.name,
        email = user?.email,
        phone = user?.phone,
        address = user?.address,
        gender = user?.gender,
        birth_date = user?.birth_date,
        image = user?.image,
        apiToken = apiToken
    )

}