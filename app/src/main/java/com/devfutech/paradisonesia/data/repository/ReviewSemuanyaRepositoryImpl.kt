package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.ProductDetailService
import com.devfutech.paradisonesia.data.network.service.ReviewLihatSemuaService
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.domain.model.review.Review
import com.devfutech.paradisonesia.domain.repository.ProductDetailRepository
import com.devfutech.paradisonesia.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReviewSemuanyaRepositoryImpl(
    private val remoteDataSource: ReviewLihatSemuaService
): ReviewRepository{
    override suspend fun listReview(index: String): Flow<List<Review>> = flow {
        val response = remoteDataSource.reviewLihatSemua(index).data?.map {
            it.toReviewLihatSemua()
        }?: listOf()
        emit(response)
    }

    override suspend fun listReview(index: String, map: Map<String, String>): Flow<List<Review?>?> = flow{
        val response = remoteDataSource.reviewLihatSemua(index, map).data?.map {
            it.toReviewLihatSemua()
        }
        emit(response)
    }
}