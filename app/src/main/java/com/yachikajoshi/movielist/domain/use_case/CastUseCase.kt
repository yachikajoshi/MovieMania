package com.yachikajoshi.movielist.domain.use_case

import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.CastResponse
import com.yachikajoshi.movielist.domain.repo.MoviesRepository
import javax.inject.Inject

class CastUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend fun getCast(movieId: String): Resource<CastResponse> {
        val response = try {
            repository.getCast(movieId)
        } catch (e: Exception) {
            return Resource.Error("An unknown exception occurred: ${e.message}")
        }
        return Resource.Success(response)
    }


}