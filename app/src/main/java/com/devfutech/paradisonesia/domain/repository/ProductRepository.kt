package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.category_product.CategoryProduct
import com.devfutech.paradisonesia.domain.model.city.City
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.province.Province
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun listBanners(): Flow<List<Banner>>

    suspend fun listCategories(): Flow<List<CategoryProduct>>

    suspend fun listCities(): Flow<List<City>>

    suspend fun listProvinces(): Flow<List<Province>>

    suspend fun listProduct(map: Map<String,String>): Flow<List<Product>>

    suspend fun listProduct(): Flow<List<Product>>

    suspend fun listProductSubCategoryProduct(): Flow<List<Product.Sub_category?>?>

}