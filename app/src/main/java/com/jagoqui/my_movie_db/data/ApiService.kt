package com.jagoqui.my_movie_db.data

import com.jagoqui.my_movie_db.data.server.ListMovies
import com.jagoqui.my_movie_db.data.server.Movies
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/top_rated")
    fun getTopRated(
        @Query("api_key") apiKey: String
    ): Call<ListMovies>

    companion object {
        val urlAPI = "https://api.themoviedb.org/3/"

        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return  retrofit.create(ApiService::class.java)
        }
    }
}