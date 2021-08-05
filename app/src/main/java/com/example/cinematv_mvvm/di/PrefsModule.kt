package com.example.cinematv_mvvm.di

import android.content.Context
import android.content.SharedPreferences
import com.example.cinematv_mvvm.data.prefs.Prefs
import com.example.cinematv_mvvm.data.prefs.PrefsImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PrefsModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun providePrefs(context: Context, gson: Gson, sharedPreferences: SharedPreferences): Prefs {
        return PrefsImpl(gson, sharedPreferences)
    }


}