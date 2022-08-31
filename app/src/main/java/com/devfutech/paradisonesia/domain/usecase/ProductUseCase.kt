package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.repository.ProductRepository
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.category_product.CategoryProduct
import com.devfutech.paradisonesia.domain.model.city.City
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.province.Province
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val repository: ProductRepository
)  {
    suspend fun getListBanner(): Flow<List<Banner>> = repository.listBanners()

    suspend fun getListCategoryProduct(): Flow<List<CategoryProduct>> = repository.listCategories()

    suspend fun getListCity(): Flow<List<City>>  = repository.listCities()

    suspend fun getListProvince(): Flow<List<Province>>  = repository.listProvinces()

    suspend fun getListProduct(map: Map<String,String>): Flow<List<Product>>  = repository.listProduct(map)

    suspend fun getListProduct(): Flow<List<Product>>  = repository.listProduct()

}