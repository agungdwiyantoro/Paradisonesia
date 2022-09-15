package com.devfutech.paradisonesia.domain.model.dynamicURL

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DynamicURL (
    val indexURL: String?
) : Parcelable