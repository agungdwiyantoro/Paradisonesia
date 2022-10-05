package com.devfutech.paradisonesia.domain.model.token

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
        val token_type: String?,
        val expires_in: String?,
        val access_token: String?,
        val refresh_token: String?,
        ) :Parcelable