package com.devfutech.paradisonesia.data.network.response.product


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?
)