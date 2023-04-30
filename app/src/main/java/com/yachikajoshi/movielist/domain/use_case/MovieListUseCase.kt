package com.yachikajoshi.movielist.domain.use_case

import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.data.model.UpcomingMovies
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import javax.inject.Inject

class MovieListUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    suspend fun getTop10MoviesList(): Resource<UpcomingMovies> {
        val response = try {
            movieRepository.getTop10Movies()
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getUpcomingMoviesList(): Resource<UpcomingMovies> {
        val response = try {
            movieRepository.getTop10Movies()
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
//    suspend fun getTopTVShows(): Resource<UpcomingMovies> {
//        val response = try {
//            movieRepository.getTopTVShows()
//        } catch (e: Exception) {
//            return Resource.Error("An unknown exception occurred: ${e.message}")
//        }
//        return Resource.Success(response)
//    }
}

