package com.devfutech.paradisonesia.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReviewLihatSemua(
    val id: Int?,
    val ratingAverage: String?,
    val ratingCount: Int?
) : Parcelable