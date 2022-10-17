package com.devfutech.paradisonesia.domain.repository
import com.devfutech.paradisonesia.domain.model.token.Token
import kotlinx.coroutines.flow.Flow

interface TokenRefreshRepository {
    fun tokenRefresh(payload:  String): String
}