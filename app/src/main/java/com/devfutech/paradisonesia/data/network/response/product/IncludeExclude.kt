package com.devfutech.paradisonesia.data.network.response.product


import com.google.gson.annotations.SerializedName

data class IncludeExclude(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_include")
    val isInclude: Int?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("updated_at")
    val updatedAt: String?
)