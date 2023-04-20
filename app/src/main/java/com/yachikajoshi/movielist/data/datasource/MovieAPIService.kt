package com.yachikajoshi.movielist.data.datasource

import com.yachikajoshi.movielist.data.model.Movies
import retrofit2.Response
import retrofit2.http.GET

interface MovieAPIService {

    @GET("MostPopularMovies/k_20dozih9")
    suspend fun getTop10Movies(): Movies

    @GET("ComingSoon/k_20dozih9")
    suspend fun getComingSoonMovies(): Movies
}