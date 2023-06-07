package com.yachikajoshi.movielist.data.datasource

import com.yachikajoshi.movielist.data.model.CastResponse
import com.yachikajoshi.movielist.data.model.MovieDetail
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.data.model.MovieTrailer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPIService {

    @GET("movie/top_rated")
    suspend fun getTop10Movies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getComingSoonMovies(@Query("api_key") apiKey: String): MovieResponse

    @GET("trending/all/week")
    suspend fun getTrending(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movieId}")
    suspend fun getDetail(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): MovieDetail

    @GET("movie/{movieId}/videos")
    suspend fun getTrailer(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): MovieTrailer

    @GET("movie/{movieId}/similar")
    suspend fun getSuggestedMovies(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("movie/{movieId}/credits")
    suspend fun movieCast(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String
    ): CastResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieResponse
}