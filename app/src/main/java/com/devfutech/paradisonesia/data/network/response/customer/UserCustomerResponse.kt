package com.devfutech.paradisonesia.data.network.response.customer


import com.devfutech.paradisonesia.domain.model.user.Customer
import com.google.gson.annotations.SerializedName

data class UserCustomerResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("is_email_verified")
    val is_email_verified: Int?,
    @SerializedName("is_new_member")
    val is_new_member: Int?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("token_type")
    val token_type: String?,
    @SerializedName("expires_in")
    val expires_in: Int?,
    @SerializedName("access_token")
    val access_token: String?,
    @SerializedName("refresh_token")
    val refresh_token: String?,
    @SerializedName("profile")
    val profile: Customer.Profile,
    @SerializedName("status")
    val status: Customer.Status,
    @SerializedName("customer_level")
    val customerLevel: Customer.Customer_Level,


    /*
    @SerializedName("customer_level_id")
    val customerLevelId: Int?,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String?,
    @SerializedName("merchant_id")
    val merchantId: Any?,
    @SerializedName("note")
    val note: Any?,
    @SerializedName("status_id")
    val statusId: Int?
     */
)