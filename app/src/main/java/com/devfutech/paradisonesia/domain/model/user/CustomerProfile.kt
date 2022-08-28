package com.devfutech.paradisonesia.domain.model.user

import java.io.File

data class CustomerProfile(
    val id: Int?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val is_email_verified: Int?,
    val is_new_member: Int?,
    val note: String?,
    val profile: Profile,
    val status: Status,
    val customer_level: Customer_Level

) {
    data class Profile(
        val id: Int?,
        val user_id: Int?,
        val birth_date: String?,
        val gender: Int?,
        val address: String?,
        val image: String?
    )

    data class Status(
        val id: Int?,
        val name: String?
    )

    data class Customer_Level(
        val id: Int?,
        val name: String?,
        val icon: String?
    )
}