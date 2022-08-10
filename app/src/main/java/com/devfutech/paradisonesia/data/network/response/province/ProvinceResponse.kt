package com.devfutech.paradisonesia.data.network.response.province


import com.devfutech.paradisonesia.domain.model.province.Province
import com.google.gson.annotations.SerializedName

data class ProvinceResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("name")
    val name: String?
){
    fun toProvince() = Province(
        code = code,
        countryCode = countryCode,
        name = name
    )
}