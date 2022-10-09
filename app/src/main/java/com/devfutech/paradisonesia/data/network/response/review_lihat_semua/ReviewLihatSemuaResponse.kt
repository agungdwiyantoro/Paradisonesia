package com.devfutech.paradisonesia.data.network.response.review_lihat_semua

import com.devfutech.paradisonesia.domain.model.review.Review
import com.google.gson.annotations.SerializedName

data class ReviewLihatSemuaResponse (
    @SerializedName("item_id")
    val item_id: Int?,
    @SerializedName("product_id")
    val product_id: Int?,
    @SerializedName("review")
    val review: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("deleted_at")
    val deleted_at: String?,
    @SerializedName("transaction_id")
    val transaction_id: Int?,
    @SerializedName("net_price")
    val net_price: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("product_name")
    val product_name: String?,
    @SerializedName("product_description")
    val product_description: String?,
    @SerializedName("start_date")
    val start_date: String?,
    @SerializedName("end_date")
    val end_date: String?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("status_id")
    val status_id:Int?
    ) {
    fun toReviewLihatSemua() = Review(
        item_id = item_id,
        product_id = product_id,
        review = review,
        rating = rating,
        deleted_at = deleted_at,
        transaction_id = transaction_id,
        net_price = net_price,
        price = price,
        product_name = product_name,
        product_description = product_description,
        start_date = start_date,
        end_date = end_date,
        quantity = quantity,
        note = note,
        status_id = status_id
    )

}


