package com.shahpourkhast.rohamgoft.data.api

import com.shahpourkhast.rohamfood.data.api.ApiService
import com.shahpourkhast.rohamfood.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //-----------------------------------------------------------------------------

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    //-----------------------------------------------------------------------------

    val api: ApiService by lazy {

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}