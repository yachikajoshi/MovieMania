package com.yachikajoshi.movielist.domain.use_case

import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.MovieDetail
import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import javax.inject.Inject

class MovieListUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    suspend fun getTop10MoviesList(): Resource<MovieResponse> {
        val response = try {
            movieRepository.getTop10Movies()
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getUpcomingMoviesList(): Resource<MovieResponse> {
        val response = try {
            movieRepository.getComingSoonMovies()
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getTrailer(movieId: String): Resource<MovieTrailer> {
        val response = try {
            movieRepository.getTrailer(movieId)
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getSuggestedMovies(movieId: String): Resource<MovieResponse> {
        val response = try {
            movieRepository.getSuggestedMovies(movieId)
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getTrendingMovies(): Resource<MovieResponse> {
        val response = try {
            movieRepository.getTrendingMovies()
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getMovieDetail(movieId: String): Resource<MovieDetail> {
        val response = try {
            movieRepository.getMovieDetail(movieId)
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }
}

