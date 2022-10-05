package com.devfutech.paradisonesia.domain.model.review

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val item_id: Int?,
    val product_id: Int?,
    val review: String?,
    val rating: Int?,
    val deleted_at: String?,
    val transaction_id: Int?,
    val net_price: Int?,
    val price: Int?,
    val product_name: String?,
    val product_description: String?,
    val start_date: String?,
    val end_date: String?,
    val quantity: Int?,
    val note: String?,
    val status_id:Int?
) : Parcelable