package com.devfutech.paradisonesia.domain.model.merchant.homeMerchant

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class HomeMerchant (
    val titleTopMerchant: String?,
    val valueTopMerchant: String?
) {
    @Parcelize
    data class MerchantProduct(
        val productName: String?,
        val productType: String?,
        val productAddress: String?,
        val productDate: String?,
        val productTotal: String?
    ) : Parcelable
}