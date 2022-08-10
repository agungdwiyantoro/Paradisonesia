package com.devfutech.paradisonesia.domain.model.province

import com.devfutech.paradisonesia.domain.model.filter.Filter

/**
 * Created by devfutech on 10/8/2021.
 */
data class Province(
    val code: Int?,
    val countryCode: String?,
    val name: String?
){
    fun toFilter() = Filter(
        id = code,
        name = name
    )
}
