package com.devfutech.paradisonesia.domain.model.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by devfutech on 10/8/2021.
 */
@Parcelize
data class SortFilter(
    val id:Int?,
    val lowestPrice:Int?,
    val highestPrice:Int?,
    val highestRating:Int
) : Parcelable
