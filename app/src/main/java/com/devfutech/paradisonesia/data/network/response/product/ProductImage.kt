package com.devfutech.paradisonesia.data.network.response.product


import com.google.gson.annotations.SerializedName

data class ProductImage(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)