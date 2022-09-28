package com.devfutech.paradisonesia.domain.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by devfutech on 10/5/2021.
 */
data class Product(
    val id: Int?,
    val merchant_id: Int?,
    val product_sub_category_id: Int?,
    val status_id: Int?,
    val name: String?,
    val description: String?,
    val address: String?,
    val coordinate: String?,
    val cityCode: Int?,
    val duration: Int?,
    val start_date: String?,
    val end_date: String?,
    val net_price: Int?,
    val price: Int?,
    val unit: String?,
    val thumbnail: String?,
    val maxPerson: String?,
    val minPerson: String?,
    val note: String?,
    val isHiglight: Int?,
    val updated_at: String?,
    val rating_average: String?,
    val reviews_count: Int?,
    val city : City?,
    val sub_category: Sub_category?,
) {
    @Parcelize
    data class City(
        val code: Int?,
        val name: String?,
        val province_code: Int?,
        val image: String?,
        val is_highlight: Int?
    ) : Parcelable

    @Parcelize
    data class Sub_category(
        val id: Int?,
        val product_sub_category_id: Int?,
        val name: String?,
        val icon: String?,
        val category: Category?
    ) : Parcelable {
        @Parcelize
        data class Category(
            val id: Int?,
            val payment_type_id: Int?,
            val name: String?,
            val icon: String?
        ) : Parcelable
    }
}