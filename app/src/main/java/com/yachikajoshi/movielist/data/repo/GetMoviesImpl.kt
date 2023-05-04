package com.yachikajoshi.movielist.data.repo

import com.yachikajoshi.movielist.common.Constants.API_KEY
import com.yachikajoshi.movielist.data.datasource.MovieAPIService
import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.domain.repo.MoviesRepository

/**
 * Here we created the implementation class for
 * the repository interface which we got from
 * `domain.repo` package. In this we are passing
 * our APIService interface to call API and retrieve data
 * **/

class GetMoviesImpl(private val movieAPIService: MovieAPIService) : MoviesRepository {

    override suspend fun getTop10Movies(): MovieResponse =
        movieAPIService.getTop10Movies(apiKey = API_KEY)

    override suspend fun getComingSoonMovies(): MovieResponse =
        movieAPIService.getComingSoonMovies(apiKey = API_KEY)

    override suspend fun getTrailer(movieId: String): MovieTrailer =
        movieAPIService.getTrailer(movieId = movieId, apiKey = API_KEY)

    override suspend fun getTrendingMovies(): MovieResponse =
        movieAPIService.getTrending(apiKey = API_KEY)

}