package com.devfutech.paradisonesia.data.network.response.RefreshToken

import com.devfutech.paradisonesia.domain.model.banner.Banner
import com.devfutech.paradisonesia.domain.model.token.Token
import com.google.gson.annotations.SerializedName

data class TokenRefreshResponse(
    @SerializedName("token_type")
    val token_type: String?,
    @SerializedName("expires_in")
    val expires_in: String?,
    @SerializedName("access_token")
    val access_token: String?,
    @SerializedName("refresh_token")
    val refresh_token: String?
) {
    fun toToken(): Token {
        return Token(
            token_type = token_type,
            expires_in = expires_in,
            access_token = access_token,
            refresh_token = refresh_token
        )
    }
}
