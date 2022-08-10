package com.devfutech.paradisonesia.data.network.response.category_product


import com.devfutech.paradisonesia.data.network.response.sub_category_product.SubCategoryProductResponse
import com.devfutech.paradisonesia.domain.model.category_product.CategoryProduct
import com.google.gson.annotations.SerializedName

data class CategoryProductResponse(
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("payment_type_id")
    val paymentTypeId: Int?,
    @SerializedName("sub_category")
    val subCategoryProductResponse: List<SubCategoryProductResponse>?
) {
    fun toCategoryProduct(): CategoryProduct {
        return CategoryProduct(
            icon = icon,
            id = id,
            name = name,
            paymentTypeId = paymentTypeId,
            subCategoryProduct = subCategoryProductResponse?.map {
                it.toSubCategoryProduct()
            } ?: emptyList()
        )
    }
}