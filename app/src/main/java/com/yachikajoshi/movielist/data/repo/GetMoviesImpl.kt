package com.yachikajoshi.movielist.data.repo

import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.datasource.MovieAPIService
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.domain.repo.MoviesRepository

/**
 * Here we created the implementation class for
 * the repository interface which we got from
 * `domain.repo` package. In this we are passing
 * our APIService interface to call API and retrieve data
 * **/

class GetMoviesImpl(private val movieAPIService: MovieAPIService) : MoviesRepository {

    override suspend fun getTop10Movies(): Movies =
        movieAPIService.getTop10Movies()

    override suspend fun getComingSoonMovies(): Movies =
        movieAPIService.getComingSoonMovies()

}