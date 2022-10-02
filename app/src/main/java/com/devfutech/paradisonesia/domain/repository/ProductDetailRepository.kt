package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    suspend fun productDetail(map: Map<String,String>) : Flow<List<ProductDetail>>
    suspend fun productDetail(index: String) : Flow<ProductDetail>
    suspend fun productDetailImages(index: String) : Flow<List<ProductDetail.Images?>?>
    suspend fun productDetailRencanaPerjalanan(index: String) : Flow<List<ProductDetail.Schedules?>?>
    suspend fun productDetailRencanaPerjalananDays(index: String) : Flow<List<ProductDetail.Schedules.Days?>?>
    suspend fun productDetailIncludeExcludes(index: String) : Flow<List<ProductDetail.Include_Excludes?>?>
    suspend fun productDetailFasilitasLayanan(index: String) : Flow<List<ProductDetail.Facilities?>?>
    suspend fun productDetailFaqs(index: String) : Flow<List<ProductDetail.Faqs?>?>
    suspend fun productDetailTerms(index: String) : Flow<List<ProductDetail.Terms?>?>
    suspend fun productDetailReviews(index: String) : Flow<List<ProductDetail.Reviews?>?>
}