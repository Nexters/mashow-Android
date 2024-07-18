package com.nexters.mashow

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

    init {
        instance = this
    }

    companion object {
        private const val APP_NAME = "ModuTaxi"
        lateinit var instance: App
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_NAME)
        fun getContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

    }

}