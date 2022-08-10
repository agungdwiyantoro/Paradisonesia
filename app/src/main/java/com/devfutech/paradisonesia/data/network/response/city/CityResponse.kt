package com.devfutech.paradisonesia.data.network.response.city


import com.devfutech.paradisonesia.domain.model.city.City
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("is_highlight")
    val isHighlight: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("province_code")
    val provinceCode: Int?
) {
    fun toCity(): City {
        return City(
            code = code,
            image = image,
            isHighlight = isHighlight,
            name = name,
            provinceCode = provinceCode
        )
    }
}