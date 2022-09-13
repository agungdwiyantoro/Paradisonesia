package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.ProductService
import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.category_product.CategoryProduct
import com.devfutech.paradisonesia.domain.model.city.City
import com.devfutech.paradisonesia.domain.model.product.Product
import com.devfutech.paradisonesia.domain.model.province.Province
import com.devfutech.paradisonesia.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val remoteDataSource: ProductService
) : ProductRepository {
    override suspend fun listBanners(): Flow<List<Banner>> = flow {
        val response =  remoteDataSource.banners().data?.map {
            it.toBanner()
        }?: listOf()
        emit(response)
    }

    override suspend fun listCategories(): Flow<List<CategoryProduct>> = flow {
        val response = remoteDataSource.categories().data?.map {
            it.toCategoryProduct()
        }?: listOf()
        emit(response)
    }

    override suspend fun listCities(): Flow<List<City>> = flow {
        val response = remoteDataSource.cities().data?.map {
            it.toCity()
        }?: listOf()
        emit(response)
    }

    override suspend fun listProvinces(): Flow<List<Province>> = flow {
        val response = remoteDataSource.provinces().data?.map {
            it.toProvince()
        }?: listOf()
        emit(response)
    }

    override suspend fun listProduct(): Flow<List<Product>> = flow {
        val response = remoteDataSource.products().data?.map {
            it.toProduct()
        }?: listOf()
        emit(response)
    }

    override suspend fun listProduct(map: Map<String, String>): Flow<List<Product>> = flow {
        val response = remoteDataSource.products(map).data?.map {
            it.toProduct()
        }?: listOf()
        emit(response)
    }
}