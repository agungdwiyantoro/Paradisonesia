package com.devfutech.paradisonesia.domain.model.advance_filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by devfutech on 10/8/2021.
 */
@Parcelize
data class AdvanceFilter(
    val id:Int?,
    val minPrice:Int?,
    val maxPrice:Int?,
    val rating: Int?,
    val startDate: String?,
    val endDate: String?
) : Parcelable
