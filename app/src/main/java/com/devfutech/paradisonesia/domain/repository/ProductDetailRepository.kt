package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    suspend fun productDetail(map: Map<String,String>): Flow<List<ProductDetail>>
}