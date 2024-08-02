package com.mashow.alchol.di

import com.mashow.data.remote.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideAuthApi(@NetworkModule.BaseRetrofit retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)

}