package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDetailUseCase @Inject constructor(
    private val repository: ProductDetailRepository
) {
    suspend fun getListProductDetail(map: Map<String, String>): Flow<List<ProductDetail>> = repository.productDetail(map)

    suspend fun getListProductDetail(index: String): Flow<ProductDetail> = repository.productDetail(index)

}