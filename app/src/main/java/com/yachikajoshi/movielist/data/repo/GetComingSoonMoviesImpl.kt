package com.yachikajoshi.movielist.data.repo

import com.yachikajoshi.movielist.data.datasource.MovieAPIService
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.domain.repo.ComingSoonRepository

class GetComingSoonMoviesImpl(private val apiService: MovieAPIService) : ComingSoonRepository {
    override suspend fun getComingSoonMovies(): Movies =
        apiService.getComingSoonMovies()

}