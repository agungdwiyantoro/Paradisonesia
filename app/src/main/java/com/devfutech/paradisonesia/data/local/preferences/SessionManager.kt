package com.devfutech.paradisonesia.data.local.preferences

import com.devfutech.paradisonesia.domain.repository.TokenRefreshRepository
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val pref: AuthPreference,
    private val authRepository: TokenRefreshRepository
) {

    fun getAccessToken(): String? = pref.getToken()

    fun getRefreshToken(): String? = pref.getRefreshToken()

    fun refreshToken(refreshToken: String): String = authRepository.tokenRefresh(refreshToken)

    fun logout() {
        /* .... */
        pref.setToken("")
    }
}