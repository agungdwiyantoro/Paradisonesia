package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.merchant.MerchantStatus
import com.devfutech.paradisonesia.domain.repository.MerchantRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

/**
 * Created by devfutech on 9/10/2021.
 */
class MerchantUseCase @Inject constructor(
    private val repository: MerchantRepository
) {
    suspend fun merchantRegister(
        payload: Map<String, String>,
        ktp: MultipartBody.Part?,
        npwp: MultipartBody.Part?,
        siup: MultipartBody.Part?
    ): Flow<String?> {
        return repository.merchantRegister(payload, ktp, npwp, siup)
    }

    suspend fun merchantStatus(): Flow<MerchantStatus?> {
        return repository.merchantStatus()
    }

}