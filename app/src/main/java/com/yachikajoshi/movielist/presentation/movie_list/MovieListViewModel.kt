package com.yachikajoshi.movielist.presentation.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.domain.use_case.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieListUseCase: MovieListUseCase) :
    ViewModel() {

    private val _movieList = MutableStateFlow(MovieState())
    val top10MovieList: StateFlow<MovieState> = _movieList
    private val _upcomingMovieList = MutableStateFlow(MovieState())
    val upcomingMovieList: StateFlow<MovieState> = _upcomingMovieList

    fun getTop10MovieList() {
        movieListUseCase.getTop10Movies().onEach {
            when (it) {
                is Resource.Error -> {
                    _movieList.value = MovieState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _movieList.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _movieList.value = MovieState(data = it.data?.items)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUpcomingMovieList() {
        movieListUseCase.getUpcomingMovies().onEach {
            when (it) {
                is Resource.Error -> {
                    _upcomingMovieList.value = MovieState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _upcomingMovieList.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _upcomingMovieList.value = MovieState(data = it.data?.items)
                }
            }
        }.launchIn(viewModelScope)
    }

}