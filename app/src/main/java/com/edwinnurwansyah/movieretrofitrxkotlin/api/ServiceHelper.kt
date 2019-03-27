package com.edwinnurwansyah.movieretrofitrxkotlin.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceHelper{
    private var BASE_URL: String = "https://api.themoviedb.org"
    private var builder: Retrofit.Builder = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    fun <S> createService(serviceClass: Class<S>): S {
        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .followRedirects(true)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Connection", "close")
                    .build()
                chain.proceed(newRequest)
            }
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        httpClient.addInterceptor(interceptor)
        val client: OkHttpClient = httpClient.build()
        val retrofit: Retrofit = builder.client(client).build()

//        httpClient.addInterceptor(interceptor)
        return retrofit.create(serviceClass)
    }
}