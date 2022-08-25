package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.user.CustomerProfile
import com.devfutech.paradisonesia.domain.repository.CustomerProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CustomerProfileUseCase @Inject constructor (
    private val repository: CustomerProfileRepository
) {
    suspend fun profileCustomer(payload: Map<String, String>): Flow<CustomerProfile?> {
        return repository.profileCustomer(payload)
    }
}
