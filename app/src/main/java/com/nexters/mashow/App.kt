package com.nexters.mashow

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.nexters.presentation.R
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

    init {
        instance = this
    }

    companion object {
        private const val APP_NAME = "Mashow"
        lateinit var instance: App
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = APP_NAME)
        fun getContext(): Context = instance.applicationContext

    }

    override fun onCreate() {
        super.onCreate()

        Log.d("debugging", "keyhash : ${Utility.getKeyHash(this)}")
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }

}