package com.devfutech.paradisonesia.di

import android.content.Context
import android.content.SharedPreferences
import androidx.viewbinding.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.devfutech.paradisonesia.data.local.preferences.AuthPreference
import com.devfutech.paradisonesia.data.local.preferences.SessionManager
import com.devfutech.paradisonesia.data.network.service.*
import com.devfutech.paradisonesia.data.repository.TokenRefreshRepositoryImpl
import com.devfutech.paradisonesia.domain.repository.TokenRefreshRepository
import com.devfutech.paradisonesia.external.config.BaseConfig
import com.devfutech.paradisonesia.external.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideRetrofitApi(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BaseConfig().getBaseUrlStagingCustomer())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /*
    @Singleton
    @Provides
    fun provideOkHttpClientApi(
        chuckerInterceptor: ChuckerInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .dispatcher(Dispatcher().apply {
                maxRequests = 1
            })
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(requestInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }


     */

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: RequestInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideLogoutService(retrofit: Retrofit) : LogoutService =
        retrofit.create(LogoutService::class.java)

    @Provides
    @Singleton
    fun provideReviewLihatSemuaService(retrofit: Retrofit) : ReviewLihatSemuaService =
        retrofit.create(ReviewLihatSemuaService::class.java)

    @Provides
    @Singleton
    fun provideProductDetailService(retrofit: Retrofit) : ProductDetailService =
        retrofit.create(ProductDetailService::class.java)

    @Provides
    @Singleton
    fun provideTokenRefreshService(retrofit: Retrofit) : TokenRefreshService =
        retrofit.create(TokenRefreshService::class.java)
    
    @Provides
    @Singleton
    fun provideCustomerService(retrofit: Retrofit): CustomerService =
        retrofit.create(CustomerService::class.java)

    @Provides
    @Singleton
    fun provideMerchantService(retrofit: Retrofit): MerchantService =
        retrofit.create(MerchantService::class.java)

    @Provides
    @Singleton
    fun provideCartService(retrofit: Retrofit): WishlistService =
        retrofit.create(WishlistService::class.java)

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(AuthPreference.SHARED_PREFS, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppSharedPreferences(
        sharedPreferences: SharedPreferences
    ) = AuthPreference(sharedPreferences)

    @Singleton
    @Provides
    fun provideAuthRepository(): TokenRefreshRepository = TokenRefreshRepositoryImpl()

    @Singleton
    @Provides
    fun provideSessionManager(
        appSharedPreferences: AuthPreference,
        authRepository: TokenRefreshRepository
    ): SessionManager = SessionManager(
        pref = appSharedPreferences,
        authRepository = authRepository
    )

    @Singleton
    @Provides
    fun provideAuthInterceptorImpl(
        sessionManager: SessionManager
    ): RequestInterceptor = RequestInterceptor(sessionManager = sessionManager)


    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector
    ): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()
    }


    @Singleton
    @Provides
    fun provideChuckerCollector(@ApplicationContext context: Context): ChuckerCollector {
        return ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
    }
}