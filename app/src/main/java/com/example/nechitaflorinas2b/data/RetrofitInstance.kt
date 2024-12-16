package com.example.nechitaflorinas2b.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://v2.jokeapi.dev/" // Base URL for the API

    val api: JokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Parse JSON into data classes
            .build()
            .create(JokeApiService::class.java)
    }
}
