package com.devfutech.paradisonesia.data.network.response.product_detail

import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.google.gson.annotations.SerializedName

data class ProductDetailResponse (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("merchant_id")
    val merchant_id: Int?,
    @SerializedName("product_sub_category_id")
    val product_sub_category_id: Int?,
    @SerializedName("status_id")
    val status_id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("coordinate")
    val coordinate: String?,
    @SerializedName("city_code")
    val city_code: Int?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("start_date")
    val start_date: String?,
    @SerializedName("end_date")
    val end_date: String?,
    @SerializedName("net_price")
    val net_price: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("unit")
    val unit: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("max_person")
    val max_person: String?,
    @SerializedName("min_person")
    val min_person: String?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("is_highlight")
    val is_highlight: Int?,
    @SerializedName("updated_at")
    val updated_at: String?,
    @SerializedName("wishlist_count")
    val wishlist_count: Int?,
    @SerializedName("reviews_count")
    val reviews_count: Int?,
    @SerializedName("rating_average")
    val rating_average: String?,
    @SerializedName("share_url")
    val share_url: String?,
    @SerializedName("sub_category")
    val sub_category: ProductDetail.Sub_Category?,
    @SerializedName("images")
    val images: ProductDetail.Images?,
    @SerializedName("status")
    val status: ProductDetail.Status?,
    @SerializedName("city")
    val city : ProductDetail.City?,
    @SerializedName("facilities")
    val facilities: ProductDetail.Facilities?,
    @SerializedName("schedules")
    val schedules: ProductDetail.Schedules?,
    @SerializedName("include_excludes")
    val include_excludes: ProductDetail.Include_Excludes?,
    @SerializedName("faqs")
    val faqs: ProductDetail.Faqs?,
    @SerializedName("terms")
    val terms: ProductDetail.Terms?
    ) {

    fun toProductDetail() = ProductDetail(
        id = id,
        merchant_id = merchant_id,
        product_sub_category_id = product_sub_category_id,
        status_id = status_id,
        name = name,
        description = description,
        address = address,
        coordinate = coordinate,
        city_code = city_code,
        duration = duration,
        start_date = start_date,
        end_date = end_date,
        net_price = net_price,
        price = price,
        unit = unit,
        thumbnail = thumbnail,
        max_person = max_person,
        min_person = min_person,
        note = note,
        is_higlight = is_highlight,
        updated_at = updated_at,
        wishlist_count = wishlist_count,
        reviews_count = reviews_count,
        rating_average = rating_average,
        share_url = share_url,
        sub_category = sub_category,
        images = images,
        status = status,
        city = city,
        facilities = facilities,
        schedules = schedules,
        include_excludes = include_excludes,
        faqs = faqs,
        terms = terms,
    )

}