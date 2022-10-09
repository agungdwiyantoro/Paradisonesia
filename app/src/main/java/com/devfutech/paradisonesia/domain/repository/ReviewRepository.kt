package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.review.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun listReview(index: String) : Flow<List<Review>>
    suspend fun listReview(index: String, map: Map<String,String>):  Flow<List<Review?>?>
}