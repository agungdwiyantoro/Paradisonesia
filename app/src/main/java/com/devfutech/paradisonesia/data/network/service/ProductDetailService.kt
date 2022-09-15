package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.product_detail.ProductDetailResponse
import com.devfutech.paradisonesia.domain.model.dynamicURL.DynamicURL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Url




interface ProductDetailService {
    @GET("products/3")
    suspend fun productsDetail(
        @QueryMap map: Map<String, String>
    ): BaseResponse<List<ProductDetailResponse>>

    @GET("products/{index}")
    suspend fun productsDetail(
       @Path(value = "index", encoded = true) indexURL : String?
    ): BaseResponse<ProductDetailResponse>
}