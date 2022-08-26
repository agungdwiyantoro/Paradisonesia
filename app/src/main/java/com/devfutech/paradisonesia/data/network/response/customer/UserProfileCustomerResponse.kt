package com.devfutech.paradisonesia.data.network.response.customer

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import com.google.gson.annotations.SerializedName

data class UserProfileCustomerResponse(
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("birth_date")
    val birth_date: String?,
    @SerializedName("image")
    val image: String?
)
