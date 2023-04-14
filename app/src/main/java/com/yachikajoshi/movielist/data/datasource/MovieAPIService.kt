package com.yachikajoshi.movielist.data.datasource

import com.yachikajoshi.movielist.data.model.Movies
import retrofit2.Response
import retrofit2.http.GET

interface MovieAPIService {

    @GET("MostPopularMovies/k_r87ghktr")
    suspend fun getTop10Movies(): Movies

    @GET("ComingSoon/k_r87ghktr")
    suspend fun getComingSoonMovies(): Movies

}