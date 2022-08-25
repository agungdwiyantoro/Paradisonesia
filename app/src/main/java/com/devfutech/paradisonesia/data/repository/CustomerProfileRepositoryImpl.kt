package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.CustomerService
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.repository.CustomerProfileRepository
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CustomerProfileRepositoryImpl (
    private val remoteDataSource: CustomerService
    ) : CustomerProfileRepository {
    override suspend fun profileCustomer(payload: Map<String, String>): Flow<Customer?> = flow {
        val response = remoteDataSource.authCustomer(payload).data?.toCustomer()
        emit(response)
    }
}

