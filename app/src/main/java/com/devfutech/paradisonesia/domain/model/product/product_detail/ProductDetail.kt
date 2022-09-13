package com.devfutech.paradisonesia.domain.model.product.product_detail

import com.devfutech.paradisonesia.domain.model.product.Product

data class ProductDetail (
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
    val wishlist_count: Int?,
    val reviews_count: Int?,
    val rating_average: String?,
    val share_url: String?,
    val sub_category: Sub_Category?,
    val images: Images?,
    val status: Status?,
    val city :City?,
    val facilities: Facilities?,
    val schedules: Schedules?,
    val include_excludes: Include_Excludes?,
    val faqs: Faqs?,
    val terms: Terms?
    ) {
    data class Sub_Category(
        val id: Int?,
        val product_category_id: Int?,
        val name: String?,
        val icon: String?,
        val category: Category?
    ){
        data class Category(
            val id: Int?,
            val payment_type_id: Int?,
            val name: String?,
            val icon: String
        )
    }

    data class Images(
        val id: Int?,
        val image: String?,
        val product_id: Int?
    )

    data class Status(
        val id: Int?,
        val name: String?
    )

    data class City(
        val code: Int?,
        val name: String?,
        val province_code: Int?,
        val image: String?,
        val is_highlight: Int?
    )

    data class Facilities(
        val id: Int?,
        val facility_id: Int?,
        val product_id: Int?,
        val created_at: String?,
        val updated_at: String?,
        val deleted_at: String?,
        val facility: Facility?
    ) {
        data class Facility(
            val id: Int?,
            val name: String?,
            val icon: String?
        )
    }

    data class Schedules(
        val id: Int?,
        val product_id: Int?,
        val title: String?,
        val order: Int?,
        val created_at: String?,
        val updated_at: String?,
        val deleted_at: String?,
        val days: ArrayList<String>?
    )

    data class Include_Excludes(
        val id: Int?,
        val product_id: Int?,
        val description: String?,
        val is_include: Int?,
        val created_at: String?,
        val updated_at: String?,
        val deleted_at: String?
    )

    data class Faqs(
        val id: Int?,
        val product_id: Int?,
        val question: String?,
        val answer: String?,
        val is_global: Int?,
        val created_at: String?,
        val updated_at: String?,
        val deleted_at: String?
    )

    data class Terms(
        val id: Int?,
        val product_id: Int?,
        val description: String?,
        val is_global: Int?,
        val created_at: String?,
        val updated_at: String?,
        val deleted_at: String?
    )
}