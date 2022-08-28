package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import kotlinx.coroutines.flow.Flow

interface CustomerProfileRepository {
    suspend fun profileCustomer() : Flow<List<CustomerProfile?>>
}