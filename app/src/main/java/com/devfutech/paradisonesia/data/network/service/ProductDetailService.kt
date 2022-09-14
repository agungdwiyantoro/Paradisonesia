package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.product_detail.ProductDetailResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ProductDetailService {
    @GET("products")
    suspend fun productsDetail(
        @QueryMap map: Map<String, String>
    ): BaseResponse<List<ProductDetailResponse>>
}