package com.yachikajoshi.movielist.domain.repo

import com.yachikajoshi.movielist.data.model.Movies

/**
* We are going to implement this repository
 * in our data layer
* */

interface MoviesRepository {

    suspend fun getTop10Movies(): Movies

    suspend fun getComingSoonMovies(): Movies
    suspend fun getTopTVShows(): Movies

}