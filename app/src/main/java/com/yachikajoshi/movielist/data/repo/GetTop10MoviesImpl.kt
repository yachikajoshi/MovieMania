package com.yachikajoshi.movielist.data.repo

import com.yachikajoshi.movielist.data.datasource.MovieAPIService
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.domain.repo.Top10MovieRepository

/**
 * Here we created the implementation class for
 * the repository interface which we got from
 * `domain.repo` package. In this we are passing
 * our APIService interface to call API and retrieve data
 * **/

class GetTop10MoviesImpl(private val movieAPIService: MovieAPIService) : Top10MovieRepository {

    override suspend fun getTop10Movies(): Movies =
        movieAPIService.getTop10Movies()

}