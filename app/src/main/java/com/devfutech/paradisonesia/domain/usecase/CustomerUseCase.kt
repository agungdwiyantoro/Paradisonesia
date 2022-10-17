package com.devfutech.paradisonesia.domain.usecase

import com.devfutech.paradisonesia.domain.model.user.Customer
import com.devfutech.paradisonesia.domain.model.user.CustomerGetDetail
import com.devfutech.paradisonesia.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by devfutech on 9/10/2021.
 */
class CustomerUseCase @Inject constructor(
    private val repository: CustomerRepository
){
    suspend fun authCustomer(name: String, email: String, user_uid: String, player_id: String, is_email_verified: String): Flow<Customer.ProfileBasic?> {
        return repository.authCustomer(name, email, user_uid, player_id, is_email_verified)
    }
    suspend fun updateCustomerProfile(name: String, email: String, phone: String, address:String, gender: Int, birth_date: String) : Flow<CustomerGetDetail.ProfileData?> {
        return repository.updateProfileCustomer(name, email, phone, address, gender, birth_date)
    }
    suspend fun customerProfile(): Flow<CustomerGetDetail> = repository.profileCustomer()
}