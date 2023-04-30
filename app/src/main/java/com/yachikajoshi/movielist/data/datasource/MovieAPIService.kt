package com.yachikajoshi.movielist.data.datasource

import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.data.model.UpcomingMovies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPIService {

    @GET("movie/top_rated")
    suspend fun getTop10Movies(@Query("api_key") apiKey: String): UpcomingMovies

    @GET("movie/upcoming")
    suspend fun getComingSoonMovies(@Query("api_key") apiKey: String): UpcomingMovies

    @GET("movie/latest")
    suspend fun getTopTVShows(@Query("api_key") apiKey: String): UpcomingMovies

    @GET("movie/{movieId}/videos")
    suspend fun getTrailer(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): MovieTrailer

}