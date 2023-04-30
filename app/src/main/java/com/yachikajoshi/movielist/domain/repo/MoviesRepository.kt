package com.yachikajoshi.movielist.domain.repo

import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.data.model.UpcomingMovies

/**
 * We are going to implement this repository
 * in our data layer
 * */

interface MoviesRepository {

    suspend fun getTop10Movies(): UpcomingMovies

    suspend fun getComingSoonMovies(): UpcomingMovies
    suspend fun getTrailer(movieId: String): MovieTrailer

//    suspend fun getTopTVShows(): UpcomingMovies

}