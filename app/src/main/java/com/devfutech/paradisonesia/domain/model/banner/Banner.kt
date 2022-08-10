package com.devfutech.paradisonesia.domain.model.banner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val id: Int?,
    val image: String?,
    val link: String?,
    val title: String?,
) : Parcelable
