package com.nexters.data.config

import android.util.Log
import com.nexters.data.Constants
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(private val dataStoreManager: DataStoreManager) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val accessToken = runBlocking {
            dataStoreManager.getAccessToken()
        }

        Log.d("token", accessToken.toString())
        val builder: Request.Builder = chain.request().newBuilder()
        accessToken?.takeIf { it.isNotEmpty() }?.let {
            builder.addHeader(Constants.AUTHORIZATION, it)
        }
        return chain.proceed(builder.build())
    }
}