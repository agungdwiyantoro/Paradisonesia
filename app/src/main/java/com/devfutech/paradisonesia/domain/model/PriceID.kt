package com.devfutech.paradisonesia.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PriceID(
    val id: Int?,
    val price: Int?
) : Parcelable