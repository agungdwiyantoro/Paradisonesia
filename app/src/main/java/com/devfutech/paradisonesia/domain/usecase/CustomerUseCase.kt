package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by devfutech on 9/10/2021.
 */
class CustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
){
    suspend fun authCustomer(payload: Map<String,String>): Flow<Customer?> {
        return repository.authCustomer(payload)
    }
}