package com.devfutech.paradisonesia.domain.model.user

import java.io.File

data class CustomerProfile(
    val name: String?,
    val email: String?,
    val phone: String?,
    val address: String?,
    val gender: String?,
    val birth_date: String?,
    val image: String?,
    val apiToken:String?,
)
