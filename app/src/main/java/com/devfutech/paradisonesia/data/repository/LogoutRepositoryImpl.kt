package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.CustomerService
import com.devfutech.paradisonesia.data.network.service.LogoutService
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerGetDetail
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import com.devfutech.paradisonesia.domain.repository.LogoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by devfutech on 9/10/2021.
 */
class LogoutRepositoryImpl(
    private val remoteDataSource: LogoutService
): LogoutRepository {
    override suspend fun logout(): Flow<Unit> = flow{
        val response = remoteDataSource.authLogout().data
        emit(response!!)
    }
}