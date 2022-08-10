package com.devfutech.paradisonesia.domain.model.category_product

import com.devfutech.paradisonesia.domain.model.filter.Filter
import com.devfutech.paradisonesia.domain.model.sub_category_product.SubCategoryProduct

class CategoryProduct(
    val icon: String?,
    val id: Int?,
    val name: String?,
    val paymentTypeId: Int?,
    val subCategoryProduct: List<SubCategoryProduct>? = listOf()
){
    fun toFilter() = subCategoryProduct?.map {
        Filter(
            id = it.id,
            name = it.name
        )
    }
}