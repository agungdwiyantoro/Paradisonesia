package com.devfutech.paradisonesia.data.local.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named

class ProfilePreference @Inject constructor(
    @Named("ProfileDataPrefs") private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val NAME = "name"
        const val IS_COMPLETE_PROFILE = "is_complete_profile"
    }

    fun getName(): String = sharedPreferences.getString(NAME, "") ?: ""

    fun setName(name: String) = sharedPreferences.edit().putString(NAME, name).apply()

    fun getIsCompleteProfile(): Boolean = sharedPreferences.getBoolean(IS_COMPLETE_PROFILE, false)

    fun setIsCompleteProfile(status: Boolean) = sharedPreferences.edit().putBoolean(
        IS_COMPLETE_PROFILE, status
    ).apply()
}