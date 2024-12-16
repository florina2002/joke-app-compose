package com.example.nechitaflorinas2b.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JokeApiService {
    @GET("joke/Any")
    suspend fun getJokes(@Query("amount") amount: Int = 10): JokeResponse

    @GET("joke/{category}")
    suspend fun getJokes(
        @Path("category") category: String, // Dynamic category
        @Query("amount") amount: Int = 10  // Default amount of jokes
    ): JokeResponse
}

data class JokeResponse(val jokes: List<Joke>)
data class Joke(
    val category: String,
    val type: String,
    val joke: String?,
    val setup: String?,
    val delivery: String?,
    val flags: Map<String, Boolean>
)
