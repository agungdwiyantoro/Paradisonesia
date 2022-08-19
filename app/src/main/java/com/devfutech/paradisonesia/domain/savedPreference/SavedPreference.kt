package com.devfutech.paradisonesia.domain.savedPreference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object SavedPreference {
    const val EMAIL = "email"
    const val USERNAME = "username"

    private fun getSharedPreference(ctx: Context?) :
            SharedPreferences?{
        return PreferenceManager.getDefaultSharedPreferences(ctx!!)
    }

    private fun editor(ctx: Context, key:String, value:String){
        getSharedPreference(
            ctx
        )?.edit()?.putString(key, value)?.apply()
    }

    fun getEmail(ctx: Context)= getSharedPreference(
        ctx
    )?.getString(EMAIL,"")

    fun setEmail(ctx: Context, email:String){
        editor(
            ctx,
            EMAIL,
            email
        )
    }

    fun setUsername(ctx: Context, username:String){
        editor(
            ctx,
            USERNAME,
            username
        )
    }

    fun getUsername(ctx: Context) = getSharedPreference(
        ctx
    )?.getString(USERNAME,"")
}