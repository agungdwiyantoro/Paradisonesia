package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.merchant.MerchantStatus
import com.devfutech.paradisonesia.domain.model.merchant.homeMerchant.HomeMerchant
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
        payloadLong: Map<String, Long>
        //ktp: MultipartBody.Part?,
     //   npwp: MultipartBody.Part?,
      //  siup: MultipartBody.Part?
    ): Flow<String?> {
        return repository.merchantRegister(payload, payloadLong/*,ktp, npwp, siup*/)
    }

    suspend fun merchantStatus(): Flow<MerchantStatus?> {
        return repository.merchantStatus()
    }

    suspend fun homeMerchant(): Flow<HomeMerchant?> {
        return repository.merchantHome()
    }
}