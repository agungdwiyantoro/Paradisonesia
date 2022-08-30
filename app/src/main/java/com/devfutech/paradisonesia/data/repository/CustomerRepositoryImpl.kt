package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.CustomerService
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by devfutech on 9/10/2021.
 */
class CustomerRepositoryImpl(
    private val remoteDataSource: CustomerService
): CustomerRepository {
    override suspend fun authCustomer(payload: Map<String,String>): Flow<Customer?>  = flow{
        val response =  remoteDataSource.authCustomer(payload).data?.toCustomer()
        emit(response)
    }

    override suspend fun profileCustomer(): Flow<List<Customer>> {
        TODO("Not yet implemented")
    }
}