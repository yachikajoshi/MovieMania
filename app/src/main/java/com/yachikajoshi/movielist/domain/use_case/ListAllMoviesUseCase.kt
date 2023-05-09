package com.yachikajoshi.movielist.domain.use_case

import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import javax.inject.Inject

class ListAllMoviesUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    suspend fun getAllTrending(page: Int): MovieResponse =
        movieRepository.getAllTrendingMovies(page = page)
}