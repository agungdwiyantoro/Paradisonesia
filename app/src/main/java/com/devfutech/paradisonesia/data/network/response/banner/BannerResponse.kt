package com.devfutech.paradisonesia.data.network.response.banner


import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.google.gson.annotations.SerializedName

data class BannerResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("title")
    val title: String?,
) {
    fun toBanner(): Banner {
        return Banner(
            id = id,
            image = image,
            link = link,
            title = title
        )
    }
}