package com.devfutech.paradisonesia.data.network.response

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("data")
    val `data`: T? = null,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)