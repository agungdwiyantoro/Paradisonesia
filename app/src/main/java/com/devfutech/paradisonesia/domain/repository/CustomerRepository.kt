package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.user.Customer
import kotlinx.coroutines.flow.Flow

/**
 * Created by devfutech on 9/10/2021.
 */

interface CustomerRepository {
    suspend fun authCustomer(name: String, email: String, user_uid: String, player_id: String, is_email_verified: String): Flow<Customer.ProfileBasic?>
    suspend fun profileCustomer() : Flow<Customer>
}