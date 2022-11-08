package com.example.jobplanet.service

import android.util.Log
import com.example.jobplanet.BuildConfig
import com.example.jobplanet.utils.Constants
import com.example.jobplanet.utils.OffsetDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val okHttpClientBuilder = OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type" ,"application/json")
                .addHeader("Accept" ,"application/json")
            chain.proceed(request.build())
        }
        .addInterceptor(HttpLoggingInterceptor { message -> Log.d("debug", "okHttpClientBuilder: $message") }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()

    private val moshi = Moshi.Builder() // adapter
        .add(KotlinJsonAdapterFactory())
        .add(OffsetDateTimeAdapter())
        .build()

    var client = Retrofit.Builder()
        .baseUrl(Constants.endpoint)
        .client(okHttpClientBuilder)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}