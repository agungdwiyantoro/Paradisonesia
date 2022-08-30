package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.repository.TokenRefreshRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val repository: TokenRefreshRepository
) {
    suspend fun tokenAuthRefresh(payload:  String): Flow<String?> {
        return repository.tokenRefresh(payload)
    }
}