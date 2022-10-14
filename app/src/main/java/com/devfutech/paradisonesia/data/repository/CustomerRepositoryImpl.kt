package com.devfutech.paradisonesia.data.repository

import com.devfutech.paradisonesia.data.network.service.CustomerService
import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerGetDetail
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by devfutech on 9/10/2021.
 */
class CustomerRepositoryImpl(
    private val remoteDataSource: CustomerService
): CustomerRepository {
    override suspend fun authCustomer(name: String, email: String, user_uid: String, player_id: String, is_email_verified: String): Flow<Customer.ProfileBasic?>  = flow{
        val response =  remoteDataSource.authCustomer(name, email, user_uid, player_id, is_email_verified).data?.toBasicProfile()
        emit(response)
    }

    override suspend fun updateProfileCustomer(
        name: String,
        email: String,
        phone: String,
        address: String,
        gender: Int,
        birth_date: String
    ): Flow<CustomerGetDetail?> = flow{
        val response = remoteDataSource.updateCustomerProfile(name, email, phone, address, gender, birth_date).data
        emit(response)
    }

    override suspend fun profileCustomer(): Flow<CustomerGetDetail> = flow {
        val response = remoteDataSource.customerProfile().data
        emit(response!!)
    }
}