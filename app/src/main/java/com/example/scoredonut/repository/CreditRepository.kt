package com.example.scoredonut.repository

import com.example.scoredonut.api.CreditApi
import com.example.scoredonut.model.CreditResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CreditRepository @Inject constructor() {

    companion object {
        const val CREDIT_URL = "endpoint.json"
        const val BASE_URL = "https://android-interview.s3.eu-west-2.amazonaws.com/"
    }

    private val creditApi: CreditApi

    init {
        creditApi = createApi()
    }

    private fun createApi(): CreditApi {
        return buildRetrofit().create(CreditApi::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    private fun buildGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    suspend fun getCreditScore(): CreditResponse? {
        return creditApi.getCreditScore(CREDIT_URL)
    }

}