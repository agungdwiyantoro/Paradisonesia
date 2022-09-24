package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.review.Review
import com.devfutech.paradisonesia.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewUseCase @Inject constructor(
    private val repository: ReviewRepository
) {
    suspend fun getListReview(index: String): Flow<List<Review>> = repository.listReview(index)
}