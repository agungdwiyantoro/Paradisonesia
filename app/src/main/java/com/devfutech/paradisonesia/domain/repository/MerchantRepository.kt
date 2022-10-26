package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.merchant.MerchantStatus
import com.devfutech.paradisonesia.domain.model.merchant.homeMerchant.HomeMerchant
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

/**
 * Created by devfutech on 9/10/2021.
 */
interface MerchantRepository {
    suspend fun merchantRegister(
        payload: Map<String, String>,
        payloadLong: Map<String, Long>
       // ktp: MultipartBody.Part?//,
       // npwp: MultipartBody.Part?,
       // siup: MultipartBody.Part?
    ): Flow<String?>

    suspend fun merchantStatus(): Flow<MerchantStatus?>
    suspend fun merchantHome(): Flow<HomeMerchant?>
}