package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerGetDetail
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import com.devfutech.paradisonesia.domain.repository.LogoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by devfutech on 9/10/2021.
 */
class LogoutUseCase @Inject constructor(
    private val repository: LogoutRepository
){
    suspend fun logout(): Flow<Unit> = repository.logout()
}