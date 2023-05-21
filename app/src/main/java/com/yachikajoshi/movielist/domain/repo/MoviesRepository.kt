package com.yachikajoshi.movielist.domain.repo

import com.yachikajoshi.movielist.data.model.CastResponse
import com.yachikajoshi.movielist.data.model.MovieDetail
import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.data.model.MovieResponse

/**
 * We are going to implement this repository
 * in our data layer
 * */

interface MoviesRepository {

    suspend fun getTop10Movies(): MovieResponse

    suspend fun getComingSoonMovies(): MovieResponse
    suspend fun getTrailer(movieId: String): MovieTrailer

    suspend fun getMovieDetail(movieId: String): MovieDetail
    suspend fun getTrendingMovies(): MovieResponse

    suspend fun getSuggestedMovies(movieId: String): MovieResponse

    suspend fun getAllTrendingMovies(page: Int): MovieResponse
    suspend fun getAllTopRatedMovies(page: Int): MovieResponse
    suspend fun getCast(movieId: String): CastResponse

}