package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.TokenRefreshService
import com.devfutech.paradisonesia.domain.model.token.Token
import com.devfutech.paradisonesia.domain.repository.TokenRefreshRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TokenRefreshRepositoryImpl (
    private val remoteDataSource: TokenRefreshService
) : TokenRefreshRepository {
    override suspend fun tokenRefresh(payload: String): Flow<String?> = flow {
        val response = remoteDataSource.authRefreshToken(payload).data?.refresh_token
        emit(response)
    }
}