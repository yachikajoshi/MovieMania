package com.yachikajoshi.movielist.domain.use_case

import com.yachikajoshi.movielist.domain.repo.Top10MovieRepository
import javax.inject.Inject

class Top10MovieListUseCase @Inject constructor(private val top10MovieRepository: Top10MovieRepository) {

}

//operator fun invoke(): Flow<Resource<Movies>> = flow {
//    try {
//
//        emit(Resource.Loading())
//
//        val response = top10MovieRepository.getTop10Movies()
//        if (response.items.isEmpty()) {
//            emit(Resource.Error(response.errorMessage))
//        } else {
//            emit(Resource.Success(response))
//        }
//
//
//    } catch (e: HttpException) {
//        emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
//    } catch (e: IOException) {
//        emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
//    } catch (e: Exception) {
//        emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
//    }
//}
