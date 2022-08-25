package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.user.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerProfileRepository {
    suspend fun profileCustomer(payload: Map<String, String>) : Flow<Customer?>
}