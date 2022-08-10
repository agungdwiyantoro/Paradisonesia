package com.devfutech.paradisonesia.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {
    @Provides
    @Named("AuthDataPrefs")
    fun provideAuthSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(
            "AuthDataPrefs",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    @Named("ProfileDataPrefs")
    fun provideProfileSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.applicationContext.getSharedPreferences(
            "ProfileDataPrefs",
            Context.MODE_PRIVATE
        )
    }
}