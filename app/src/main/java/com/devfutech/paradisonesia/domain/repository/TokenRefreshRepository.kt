package com.devfutech.paradisonesia.domain.repository
import kotlinx.coroutines.flow.Flow

interface TokenRefreshRepository {
    suspend fun tokenRefresh(payload:  String): Flow<String?>
}