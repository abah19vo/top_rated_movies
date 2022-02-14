package com.example.sogeti.mywatchlist.services

import com.example.sogeti.mywatchlist.models.MovieDetails
import com.example.sogeti.mywatchlist.models.MovieList
import com.google.gson.Gson

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.http.Query


object ApiInstance {

    const val apiKey = "62d9771eb44dd66348e6db2bf78b88ea"

    private const val baseUrlv3 = "https://api.themoviedb.org/3/"

    private var gson: Gson = GsonBuilder()
        .create()


    private val baseApiRetrofit by lazy<Retrofit>() {
        Retrofit.Builder()
            .baseUrl(baseUrlv3)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    val movieService: ApiMovieEndpoints by lazy {
        baseApiRetrofit.create(ApiMovieEndpoints::class.java)

    }


}


interface ApiMovieEndpoints {
    // Callback for the parsed response is the last parameter
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") key: String = ApiInstance.apiKey
    ): Response<MovieDetails>

    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("pageNumber") pageNumber: Int,
        @Query("api_key") key: String = ApiInstance.apiKey
    ): Response<MovieList>
}












