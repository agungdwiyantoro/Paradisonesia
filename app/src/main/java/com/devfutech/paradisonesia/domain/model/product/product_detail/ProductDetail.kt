package com.devfutech.paradisonesia.domain.model.product.product_detail

data class ProductDetail (
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
    val price: Int?,
    val unit: String?,
    val thumbnail: String?,
    val max_person: String?,
    val min_person: String?,
    val note: String?,
    val is_higlight: Int?,
    val updated_at: String?,
    val wishlist_count: Int?,
    val reviews_count: Int?,
    val rating_average: String?,
    val share_url: String?,
    val sub_category: Sub_Category?,
    val images: List<Images?>,
    val status: Status?,
    val city :City?,
    val facilities: List<Facilities?>,
    val schedules: List<Schedules?>,
    val include_excludes: List<Include_Excludes?>,
    val faqs: List<Faqs?>,
    val terms: List<Terms?>,
    val reviews: List<Reviews?>
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
        val facility: List<Facility?>
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
        val days: List<Days>?
    ) {
        data class Days(
            val id: Int?,
            val schedule_id: Int?,
            val start_time: String?,
            val end_time: String?,
            val description: String?,
            val created_at: String?,
            val updated_at: String?,
            val deleted_at: String?
        )
    }

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

    data class Reviews(
        val item_id: Int?,
        val product_id: Int?,
        val review: String?,
        val rating: Int?,
        val deleted_at: String?
    )
}