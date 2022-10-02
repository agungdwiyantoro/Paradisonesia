package com.devfutech.paradisonesia.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PriceID(
    val id: Int?,
    val subCategoryId:Int?,
    val price: Int?,
    val ratingAverage: String?,
    val ratingCount: Int?
) : Parcelable