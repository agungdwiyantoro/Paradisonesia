package com.devfutech.paradisonesia.data.network.response.product

import com.google.gson.annotations.SerializedName

/**
 * Created by devfutech on 10/9/2021.
 */
data class DataProductResponse(
    @SerializedName("address")
    val address: String?,
    @SerializedName("avg_rating")
    val avgRating: String?,
    @SerializedName("city_code")
    val cityCode: Int?,
    @SerializedName("coordinate")
    val coordinate: Any?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("deleted_at")
    val deletedAt: Any?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("discount")
    val discount: Int?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("faqs")
    val faqs: List<Faq>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("include_excludes")
    val includeExcludes: List<IncludeExclude>?,
    @SerializedName("is_higlight")
    val isHiglight: Int?,
    @SerializedName("max_person")
    val maxPerson: String?,
    @SerializedName("merchant_id")
    val merchantId: Int?,
    @SerializedName("min_person")
    val minPerson: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("net_price")
    val netPrice: Int?,
    @SerializedName("note")
    val note: Any?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("product_images")
    val productImages: List<ProductImage>?,
    @SerializedName("product_sub_category_id")
    val productSubCategoryId: Int?,
    @SerializedName("reviews")
    val reviews: List<Review>?,
    @SerializedName("schedules")
    val schedules: List<Schedule>?,
    @SerializedName("start_date")
    val startDate: String?,
    @SerializedName("status_id")
    val statusId: Int?,
    @SerializedName("sub_category")
    val subCategory: SubCategory?,
    @SerializedName("terms")
    val terms: List<Term>?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)
