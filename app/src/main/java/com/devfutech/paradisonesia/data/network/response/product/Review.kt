package com.devfutech.paradisonesia.data.network.response.product


import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("review")
    val review: String?,
    @SerializedName("transaction_id")
    val transactionId: Int?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("user_id")
    val userId: Int?
)