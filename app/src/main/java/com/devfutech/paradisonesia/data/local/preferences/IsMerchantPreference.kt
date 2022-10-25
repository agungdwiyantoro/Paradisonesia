package com.devfutech.paradisonesia.data.local.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named

class IsMerchantPreference @Inject constructor(
    //@Named("ProfileDataPrefs") private val sharedPreferences: SharedPreferences
    private val sharedPreferences: SharedPreferences)
{

     companion object {
         const val IS_MERCHANT_PREFERENCE = "IS_MERCHANT_PREFERENCE"
         const val IS_REGISTERED_MERCHANT = "IS_REGISTERED_MERCHANT"
     }


    fun getIsMerchant(): Boolean = sharedPreferences.getBoolean(IS_MERCHANT_PREFERENCE, false)

    fun setIsMerchant(ismMerchantValue: Boolean) = sharedPreferences.edit().putBoolean(IS_MERCHANT_PREFERENCE, ismMerchantValue).apply()

    fun getIsRegisteredMerchant() : Boolean = sharedPreferences.getBoolean(IS_REGISTERED_MERCHANT, false)

    fun setIsRegisteredMerchant(isRegisteredMerchant: Boolean){
         sharedPreferences.edit().putBoolean(IS_REGISTERED_MERCHANT, isRegisteredMerchant).apply()
     }

}