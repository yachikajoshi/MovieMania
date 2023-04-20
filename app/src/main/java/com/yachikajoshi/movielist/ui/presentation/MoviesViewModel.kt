package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.domain.use_case.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieListUseCase: MovieListUseCase) :
    ViewModel() {
    init {
        getTop10MovieList()
        getUpcomingMovieList()
    }

    private var _movieState = mutableStateOf(MovieState())
    val movieState get() = _movieState

    private fun getTop10MovieList() {
        viewModelScope.launch {
            val response = movieListUseCase.getTop10MoviesList()
            when (response) {
                is Resource.Error -> {
                    _movieState.value = MovieState(error = response.message ?: "")
                }
                is Resource.Loading -> {
                    _movieState.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _movieState.value = MovieState(data = response.data!!.items)
                }
            }
        }
    }

    private val _upcomingMovieList = mutableStateOf(MovieState())
    val upcomingMovieList get() = _upcomingMovieList

    private fun getUpcomingMovieList() {
        viewModelScope.launch {
            when (val response = movieListUseCase.getUpcomingMoviesList()) {
                is Resource.Error -> {
                    _movieState.value = MovieState(error = response.message ?: "")
                }
                is Resource.Loading -> {
                    _movieState.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _movieState.value = MovieState(data = response.data!!.items)
                }
            }
        }
    }

}

data class MovieState(
    var data: List<Movies.MovieDetail> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)