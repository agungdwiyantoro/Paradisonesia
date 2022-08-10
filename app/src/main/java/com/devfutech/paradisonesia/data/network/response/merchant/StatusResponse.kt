package com.devfutech.paradisonesia.data.network.response.merchant


import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("color")
    val color: Any?,
    @SerializedName("icon")
    val icon: Any?,
    @SerializedName("name")
    val name: String?
)