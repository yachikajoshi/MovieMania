package com.yachikajoshi.movielist.domain.use_case

import androidx.lifecycle.LiveData
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    suspend fun getTop10MoviesList(): Resource<Movies> {
        val response = try {
            movieRepository.getTop10Movies()
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occured: ${e.message}")
        }
        return Resource.Success(response)
    }

    suspend fun getUpcomingMoviesList(): Resource<Movies> {
        val response = try {
            movieRepository.getTop10Movies()
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occured: ${e.message}")
        }
        return Resource.Success(response)
    }
    suspend fun getTopTVShows(): Resource<Movies> {
        val response = try {
            movieRepository.getTopTVShows()
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occured: ${e.message}")
        }
        return Resource.Success(response)
    }
}

