package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.CustomerProfileService
import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import com.devfutech.paradisonesia.domain.repository.CustomerProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CustomerProfileRepositoryImpl (
    private val remoteDataSource: CustomerProfileService
    ) : CustomerProfileRepository {
    override suspend fun profileCustomer(payload: Map<String, String>): Flow<CustomerProfile?> = flow {
        val response = remoteDataSource.CustomerProfile(payload).data?.toCustomerProfile()
        emit(response)
    }
}

