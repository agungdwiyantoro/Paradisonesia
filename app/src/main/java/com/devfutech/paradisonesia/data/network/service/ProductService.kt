package com.devfutech.paradisonesia.data.network.service

import com.devfutech.paradisonesia.data.network.response.BaseResponse
import com.devfutech.paradisonesia.data.network.response.banner.BannerResponse
import com.devfutech.paradisonesia.data.network.response.category_product.CategoryProductResponse
import com.devfutech.paradisonesia.data.network.response.city.CityResponse
import com.devfutech.paradisonesia.data.network.response.product.ProductResponse
import com.devfutech.paradisonesia.data.network.response.province.ProvinceResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ProductService {
    @GET("banners")
    suspend fun banners(): BaseResponse<List<BannerResponse>>

    @GET("product-categories")
    suspend fun categories(): BaseResponse<List<CategoryProductResponse>>

    @GET("cities")
    suspend fun cities(): BaseResponse<List<CityResponse>>

    @GET("provinces")
    suspend fun provinces(): BaseResponse<List<ProvinceResponse>>

    @GET("products")
    suspend fun products(
        @QueryMap map: Map<String, String>
    ): BaseResponse<ProductResponse>
}