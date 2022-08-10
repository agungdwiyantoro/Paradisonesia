package com.devfutech.paradisonesia.data.network.response.sub_category_product


import com.devfutech.paradisonesia.domain.model.sub_category_product.SubCategoryProduct
import com.google.gson.annotations.SerializedName

data class SubCategoryProductResponse(
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("product_category_id")
    val productCategoryId: Int?
) {
    fun toSubCategoryProduct(): SubCategoryProduct {
        return SubCategoryProduct(
            icon = icon,
            id = id,
            name = name,
            productCategoryId = productCategoryId
        )
    }
}