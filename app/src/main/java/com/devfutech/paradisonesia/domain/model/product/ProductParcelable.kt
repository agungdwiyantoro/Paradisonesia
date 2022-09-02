package com.devfutech.paradisonesia.domain.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductParcelable(
    val name : String?,
    val coordinate : String?,
    val rating_average : String?,
    val reviews_count : Int?,
    val description : String?,
    val sub_category_name : String?

) : Parcelable