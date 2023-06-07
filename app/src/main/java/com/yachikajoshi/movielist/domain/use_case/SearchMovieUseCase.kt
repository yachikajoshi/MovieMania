package com.yachikajoshi.movielist.domain.use_case

import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {
    suspend fun searchMovie(query: String, page: Int): MovieResponse =
        moviesRepository.searchMovie(query = query, page = page)
}