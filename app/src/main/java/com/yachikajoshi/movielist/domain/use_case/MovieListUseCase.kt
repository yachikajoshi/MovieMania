package com.yachikajoshi.movielist.domain.use_case

import androidx.lifecycle.LiveData
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListUseCase @Inject constructor(private val movieRepository: MoviesRepository) {

    fun getTop10Movies(): Flow<Resource<Movies>> = flow {
        try {
            emit(Resource.Loading())

            val response = movieRepository.getTop10Movies()
            if (response.items.isEmpty()) {
                emit(Resource.Error(response.errorMessage))
            } else {
                emit(Resource.Success(response))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
    fun getUpcomingMovies(): Flow<Resource<Movies>> = flow {
        try {

            emit(Resource.Loading())

            val response = movieRepository.getComingSoonMovies()
            if (response.items.isEmpty()) {
                emit(Resource.Error(response.errorMessage))
            } else {
                emit(Resource.Success(response))
            }

        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}

