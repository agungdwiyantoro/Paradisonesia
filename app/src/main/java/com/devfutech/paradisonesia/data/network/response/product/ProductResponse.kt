package com.devfutech.paradisonesia.data.network.response.product


import com.devfutech.paradisonesia.domain.model.product.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val products: List<DataProductResponse>? = listOf(),
    @SerializedName("city_code")
    val cityCode: String?,
   // @SerializedName("product_sub_category_id")
   // val productSubCategoryId: String?,
    @SerializedName("page")
    val page: String?,
    @SerializedName("show")
    val show: String?,
    @SerializedName("sort_by")
    val sortBy: String?,
    @SerializedName("sort_type")
    val sortType: String?
) {
    fun toProduct() = products?.map { product ->
        Product(
            id = product.id,
            merchant_id = product.merchantId,
            product_sub_category = product.productSubCategoryId
            address = product.address,
            avgRating = product.avgRating,
            cityCode = product.cityCode,
            description = product.description,
            discount = product.discount,
            duration = product.duration,
            endDate = product.endDate,

            isHiglight = product.isHiglight,
            maxPerson = product.maxPerson,
            minPerson = product.minPerson,
            name = product.name,
            netPrice = product.netPrice,
            price = product.price,
            thumbnail = product.thumbnail,
            unit = product.unit
        )
    }
}