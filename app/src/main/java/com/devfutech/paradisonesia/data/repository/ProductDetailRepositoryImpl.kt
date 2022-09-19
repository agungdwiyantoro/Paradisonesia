package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.ProductDetailService
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductDetailRepositoryImpl(
    private val remoteDataSource: ProductDetailService
):
    ProductDetailRepository{
    override suspend fun productDetail(map: Map<String, String>): Flow<List<ProductDetail>> = flow{
        val response = remoteDataSource.productsDetail(map).data?.map {
            it.toProductDetail()
        }?: listOf()
        emit(response)
    }

    override suspend fun productDetail(index: String): Flow<ProductDetail> = flow{
        val response = remoteDataSource.productsDetail(index).data?.toProductDetail()
        emit(response!!)
    }

    override suspend fun productDetailImages(index: String): Flow<List<ProductDetail.Images?>?> = flow {
        val response = remoteDataSource.productsDetail(index).data?.toProductDetail()?.images
        emit(response)
    }

    override suspend fun productDetailRencanaPerjalanan(index: String): Flow<List<ProductDetail.Schedules?>?> = flow {
        val response = remoteDataSource.productsDetail(index).data?.toProductDetail()?.schedules
        emit(response)
    }

    override suspend fun productDetailRencanaPerjalananDays(index: String): Flow<List<ProductDetail.Schedules.Days?>?> = flow{
        val response = remoteDataSource.productsDetail(index).data?.toProductDetail()?.schedules?.get(0)?.days
        emit(response)
    }


    override suspend fun productDetailIncludeExcludes(index: String): Flow<List<ProductDetail.Include_Excludes?>?> = flow{
        val response = remoteDataSource.productsDetail(index).data?.toProductDetail()?.include_excludes
        emit(response)
    }

    override suspend fun productDetailFasilitasLayanan(index: String): Flow<List<ProductDetail.Facilities?>?> = flow{
        val response = remoteDataSource.productsDetail(index).data?.toProductDetail()?.facilities
        emit(response)
    }

    override suspend fun productDetailReviews(index: String): Flow<List<ProductDetail.Reviews?>?> = flow {
        val response = remoteDataSource.productsDetail(index).data?.toProductDetail()?.reviews
        emit(response)
    }

}