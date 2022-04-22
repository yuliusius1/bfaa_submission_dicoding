package com.coba.githubuseryulius.api

import com.coba.githubuseryulius.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{

        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor{
                    val original = it.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", BuildConfig.API_KEY)
                    val request = requestBuilder.build()
                    it.proceed(request)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}