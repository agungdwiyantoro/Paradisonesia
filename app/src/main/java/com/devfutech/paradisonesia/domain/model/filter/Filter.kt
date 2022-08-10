package com.devfutech.paradisonesia.domain.model.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by devfutech on 10/8/2021.
 */
@Parcelize
data class Filter(
    val id:Int?,
    val name:String?,
) : Parcelable
