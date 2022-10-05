package com.devfutech.paradisonesia.data.network.response.customer


import com.devfutech.paradisonesia.domain.model.user.Customer
import com.google.gson.annotations.SerializedName

data class CustomerAuthResponse(
    @SerializedName("token")
    val token: UserCustomerResponse?,
    @SerializedName("user")
    val user: UserCustomerResponse?,
    @SerializedName("user_uid")
    val userUid: Boolean?
){
    fun toCustomer() = Customer(
        id = user?.id,
        name = user?.name,
        email = user?.email,
        phone = user?.phone,
        is_email_verified = user?.is_email_verified,
        is_new_member = user?.is_new_member,
        note = user?.note,
        token_type = token?.token_type,
        expires_in =  token?.expires_in,
        access_token = token?.access_token,
        refresh_token = token?.refresh_token,
        profile = Customer.Profile(
            id = user?.profile?.id,
            user_id = user?.profile?.user_id,
            birth_date = user?.profile?.birth_date,
            gender = user?.profile?.gender,
            address = user?.profile?.address,
            image = user?.profile?.image
        ),
        status = Customer.Status(
            id = user?.status?.id,
            name = user?.status?.name
        ),
        customer_level = Customer.Customer_Level(
            id = user?.customerLevel?.id,
            name = user?.customerLevel?.name,
            icon = user?.customerLevel?.icon
        )
        /*
        customerLevelId = user?.customerLevelId,
        merchantId = user?.merchantId,
        statusId = user?.statusId,
        apiToken = apiToken

         */
    )

    fun toBasicProfile() = Customer.ProfileBasic(

        name = user?.name,
        email = user?.email,
        phone = user?.phone,
        is_email_verified = user?.is_email_verified

    )

}