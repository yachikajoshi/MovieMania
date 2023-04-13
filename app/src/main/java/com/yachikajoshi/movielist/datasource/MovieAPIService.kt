package com.yachikajoshi.movielist.datasource

import com.yachikajoshi.movielist.datasource.model.Movies
import retrofit2.Response
import retrofit2.http.GET

interface MovieAPIService {

    @GET("MostPopularMovies/k_r87ghktr")
    fun getTop10Movies(): Response<Movies>

}