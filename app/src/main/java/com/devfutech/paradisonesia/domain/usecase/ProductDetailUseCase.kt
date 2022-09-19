package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDetailUseCase @Inject constructor(
    private val repository: ProductDetailRepository
) {
    suspend fun getListProductDetail(map: Map<String, String>): Flow<List<ProductDetail>> = repository.productDetail(map)

    suspend fun getListProductDetail(index: String): Flow<ProductDetail> = repository.productDetail(index)

    suspend fun getListProductDetailImages(index: String) : Flow<List<ProductDetail.Images?>?> = repository.productDetailImages(index)
    suspend fun getListProductDetailSchedules(index: String) : Flow<List<ProductDetail.Schedules?>?> = repository.productDetailRencanaPerjalanan(index)
    suspend fun getListProductDetailSchdulesDays(index: String) : Flow<List<ProductDetail.Schedules.Days?>?> = repository.productDetailRencanaPerjalananDays(index)
    suspend fun getListProductDetailIncludeExcludes(index: String) : Flow<List<ProductDetail.Include_Excludes?>?> = repository.productDetailIncludeExcludes(index)
    suspend fun getListProductDetailFasilitasLayanan(index: String) : Flow<List<ProductDetail.Facilities?>?> = repository.productDetailFasilitasLayanan(index)
    suspend fun getListProductDetailReviews(index: String): Flow<List<ProductDetail.Reviews?>?> = repository.productDetailReviews(index)

}