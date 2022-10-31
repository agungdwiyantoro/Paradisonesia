package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerGetDetail
import kotlinx.coroutines.flow.Flow

/**
 * Created by devfutech on 9/10/2021.
 */

interface LogoutRepository {
    suspend fun logout(): Flow<Unit>
}