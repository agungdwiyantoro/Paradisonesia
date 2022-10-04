package com.devfutech.paradisonesia.domain.model.product.product_detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDetailPaketLainnya (
    val id: Int?,
    val merchant_id: Int?,
    val product_sub_category_id: Int?,
    val status_id: Int?,
    val name: String?,
    val description: String?,
    val address: String?,
    val coordinate: String?,
    val city_code: Int?,
    val duration: Int?,
    val start_date: String?,
    val end_date: String?,
    val net_price: Int?,
    val price: Int?
    ) : Parcelable

