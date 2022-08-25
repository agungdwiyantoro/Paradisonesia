package com.devfutech.paradisonesia.di

import com.devfutech.paradisonesia.data.network.service.CustomerProfileService
import com.devfutech.paradisonesia.data.network.service.CustomerService
import com.devfutech.paradisonesia.data.network.service.MerchantService
import com.devfutech.paradisonesia.data.network.service.ProductService
import com.devfutech.paradisonesia.data.repository.CustomerProfileRepositoryImpl
import com.devfutech.paradisonesia.data.repository.CustomerRepositoryImpl
import com.devfutech.paradisonesia.data.repository.MerchantRepositoryImpl
import com.devfutech.paradisonesia.data.repository.ProductRepositoryImpl
import com.devfutech.paradisonesia.domain.repository.CustomerProfileRepository
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import com.devfutech.paradisonesia.domain.repository.MerchantRepository
import com.devfutech.paradisonesia.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by devfutech on 9/10/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCustomerProfileRepository(
        remoteDataSource: CustomerProfileService,
    ) : CustomerProfileRepository {
        return CustomerProfileRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(
        remoteDataSource: CustomerService,
    ): CustomerRepository {
        return CustomerRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideMerchantRepository(
        remoteDataSource: MerchantService,
    ): MerchantRepository {
        return MerchantRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        remoteDataSource: ProductService
    ): ProductRepository {
        return ProductRepositoryImpl(remoteDataSource)
    }
}