package com.github.emmpann.cinemaapp.core.remote.retrofit

import com.github.emmpann.cinemaapp.core.remote.response.DetailResponse
import com.github.emmpann.cinemaapp.core.remote.response.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing?")
    suspend fun getNowPlayingMovie(
        @Query("api_key") apiKey: String,
    ): Response

    @GET("movie/upcoming?")
    suspend fun getUpcomingMovie(
        @Query("api_key") apiKey: String,
    ): Response

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): DetailResponse

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1ZjQwOGI0NWY4MWU5MmZmOTUyMTM3NDliYjE1OGY5NiIsInN1YiI6IjY1NzFiYmYyYjA0NjA1MDEwMDFiMGZhOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.UFPG90htB5yzR1LVucl78tuSVEHDcPCX-efotQwHErw")
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): Response
}