package com.yachikajoshi.movielist.presentation

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
class MoviesViewModel @Inject constructor(private val movieListUseCase: MovieListUseCase) :
    ViewModel() {
    init {
        getTop10MovieList()
    }

    private val _movieState = MutableStateFlow(MovieState())
    val movieState: StateFlow<MovieState> = _movieState

    private val _upcomingMovieList = MutableStateFlow(MovieState())
    val upcomingMovieList: StateFlow<MovieState> = _upcomingMovieList

    private fun getTop10MovieList() {
        movieListUseCase.getTop10Movies().onEach {
            when (it) {
                is Resource.Error -> {
                    _movieState.value = MovieState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _movieState.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _movieState.value = MovieState(data = it.data.items)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUpcomingMovieList() {
        movieListUseCase.getUpcomingMovies().onEach {
            when (it) {
                is Resource.Error -> {
                    _upcomingMovieList.value = MovieState(error = it.message ?: "")
                }
                is Resource.Loading -> {
                    _upcomingMovieList.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _upcomingMovieList.value = MovieState(data = it.data.items)
                }
            }
        }.launchIn(viewModelScope)
    }

}