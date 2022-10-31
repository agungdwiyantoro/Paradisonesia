package com.devfutech.paradisonesia.di

import com.devfutech.paradisonesia.data.network.service.*
import com.devfutech.paradisonesia.data.repository.*
import com.devfutech.paradisonesia.domain.model.product.product_detail.ProductDetail
import com.devfutech.paradisonesia.domain.repository.*
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

/*
    @Provides
    @Singleton
    fun provideTokenRefreshRepository(
        remoteDataSource: TokenRefreshService,
    ): TokenRefreshRepository {
        return TokenRefreshRepositoryImpl(remoteDataSource)
    }


 */
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

    @Provides
    @Singleton
    fun provideProductDetailRepository(
        remoteDataSource: ProductDetailService
    ) : ProductDetailRepository {
        return ProductDetailRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideProductReviewsLihatSemuaRepository(
        remoteDataSource: ReviewLihatSemuaService
    ) : ReviewRepository {
        return ReviewSemuanyaRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideLogoutRepository(
        remoteDataSource: LogoutService
    ) : LogoutRepository {
        return LogoutRepositoryImpl(remoteDataSource)
    }
}