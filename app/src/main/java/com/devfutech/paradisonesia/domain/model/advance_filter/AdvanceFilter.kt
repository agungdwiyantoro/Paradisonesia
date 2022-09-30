package com.devfutech.paradisonesia.domain.model.advance_filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by devfutech on 10/8/2021.
 */
@Parcelize
data class AdvanceFilter(
    val price: List<Int?>,
    val rating: Int?,
    val date: List<Int>
) : Parcelable
