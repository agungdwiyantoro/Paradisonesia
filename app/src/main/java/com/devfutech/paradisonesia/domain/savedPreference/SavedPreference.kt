package com.devfutech.paradisonesia.domain.savedPreference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import javax.inject.Inject
import javax.inject.Named

object SavedPreference {
    const val TOKEN = "TOKEN"

    private fun getSharedPreference(ctx: Context?) :
            SharedPreferences?{
        return PreferenceManager.getDefaultSharedPreferences(ctx!!)
    }

    private fun editor(ctx: Context, key:String, value:String){
        getSharedPreference(
            ctx
        )?.edit()?.putString(key, value)?.apply()
    }

    fun getToken(ctx: Context)= getSharedPreference(
        ctx
    )?.getString(TOKEN,"")

    fun setToken(ctx: Context, tokenValue:String){
        editor(
            ctx,
            TOKEN,
            tokenValue
        )
    }
}

class AuthPreference @Inject constructor(
    @Named("AuthDataPrefs") private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val TOKEN = "token"
    }

    fun getToken(): String = sharedPreferences.getString(TOKEN, "") ?: ""

    fun setToken(token: String) = sharedPreferences.edit().putString(TOKEN, token).apply()
}
