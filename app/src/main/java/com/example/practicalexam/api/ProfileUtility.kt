package com.example.practicalexam.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProfileUtility {

    fun getDetailsUser(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        var client: OkHttpClient = OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization",
                    "Bearer " + "4L1YpgPnsb3M3tqt6Jhfa9H6xDw0RIUPYaQk41K8hAKWilYg41hP3P60Er7RRw8v8dCpLhiMqYa9Q2hA")
                .build()
            return@Interceptor  chain.proceed(newRequest)
        }).addInterceptor(interceptor).build()



        return Retrofit.Builder()
            .baseUrl("http://admin.tmween.com/api/v1/customer/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

}