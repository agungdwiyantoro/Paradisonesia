package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.product_detail.ProductDetailResponse
import com.devfutech.paradisonesia.data.network.response.review_lihat_semua.ReviewLihatSemuaResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ReviewLihatSemuaService {
    @GET("products/{index}/review")
    suspend fun reviewLihatSemua(
        @Path(value = "index", encoded = true) indexURL : String?
    ): BaseResponse<List<ReviewLihatSemuaResponse>>
}