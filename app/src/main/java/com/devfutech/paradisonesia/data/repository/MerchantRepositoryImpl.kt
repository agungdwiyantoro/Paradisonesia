package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.MerchantService
import com.devfutech.paradisonesia.domain.model.merchant.MerchantStatus
import com.devfutech.paradisonesia.domain.repository.MerchantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.http.Part

/**
 * Created by devfutech on 9/10/2021.
 */
class MerchantRepositoryImpl(
    private val remoteDataSource: MerchantService
) : MerchantRepository {
    override suspend fun merchantRegister(
        payload: Map<String, String>,
        ktp: MultipartBody.Part?,
        npwp: MultipartBody.Part?,
        siup: MultipartBody.Part?
    ): Flow<String?> = flow {
        val response = remoteDataSource.merchantRegister(payload, ktp, npwp, siup).message
        emit(response)
    }

    override suspend fun merchantStatus(): Flow<MerchantStatus?> = flow {
        val response = remoteDataSource.merchantStatus().data?.toMerchantStatus()
        emit(response)
    }
}