package com.devfutech.paradisonesia.domain.repository

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerGetDetail
import kotlinx.coroutines.flow.Flow

/**
 * Created by devfutech on 9/10/2021.
 */

interface CustomerRepository {
    suspend fun authCustomer(name: String, email: String, user_uid: String, player_id: String, is_email_verified: String): Flow<Customer.ProfileBasic?>
    suspend fun updateProfileCustomer(name: String, email: String, phone: String, address:String, gender: Int, birth_date: String): Flow<CustomerGetDetail.ProfileData?>
    suspend fun profileCustomer() : Flow<CustomerGetDetail>
}