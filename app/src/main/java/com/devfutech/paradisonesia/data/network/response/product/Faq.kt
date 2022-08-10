package com.devfutech.paradisonesia.data.network.response.product


import com.google.gson.annotations.SerializedName

data class Faq(
    @SerializedName("answer")
    val answer: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_global")
    val isGlobal: Int?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("question")
    val question: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)