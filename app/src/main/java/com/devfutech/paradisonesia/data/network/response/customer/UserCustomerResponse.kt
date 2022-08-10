package com.devfutech.paradisonesia.data.network.response.customer


import com.google.gson.annotations.SerializedName

data class UserCustomerResponse(
    @SerializedName("customer_level_id")
    val customerLevelId: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("merchant_id")
    val merchantId: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("note")
    val note: Any?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("status_id")
    val statusId: Int?
)