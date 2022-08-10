package com.devfutech.paradisonesia.domain.model.user


/**
 * Created by devfutech on 9/14/2021.
 */
data class Customer(
    val customerLevelId: Int?,
    val email: String?,
    val id: Int?,
    val merchantId: Any?,
    val name: String?,
    val phone: String?,
    val statusId: Int?,
    val apiToken:String?,
)
