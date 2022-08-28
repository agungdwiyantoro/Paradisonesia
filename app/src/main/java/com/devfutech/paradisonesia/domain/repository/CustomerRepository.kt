package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import kotlinx.coroutines.flow.Flow

/**
 * Created by devfutech on 9/10/2021.
 */

interface CustomerRepository {
    suspend fun authCustomer(payload: Map<String,String>): Flow<Customer?>
    suspend fun profileCustomer() : Flow<List<Customer>>
}