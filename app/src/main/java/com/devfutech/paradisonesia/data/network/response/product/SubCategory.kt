package com.devfutech.paradisonesia.data.network.response.product


import com.google.gson.annotations.SerializedName

data class SubCategory(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("icon")
    val icon: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("product_category_id")
    val productCategoryId: Int?
)