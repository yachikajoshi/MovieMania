package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.data.model.toMovie
import com.yachikajoshi.movielist.domain.model.Movie
import com.yachikajoshi.movielist.domain.model.MovieDetail
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
        getTopTVShows()
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
                    _movieState.value =
                        MovieState(data = response.data!!.items.take(15))
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
                    _movieState.value =
                        MovieState(data = response.data!!.items.take(15))
                }
            }
        }
    }

    private val _tvShows = mutableStateOf(MovieState())
    val tvShows get() = _tvShows

    private fun getTopTVShows() {
        viewModelScope.launch {
            when (val response = movieListUseCase.getTopTVShows()) {
                is Resource.Error -> {
                    _tvShows.value = MovieState(error = response.message ?: "")
                }
                is Resource.Loading -> {
                    _tvShows.value = MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    _tvShows.value =
                        MovieState(data = response.data!!.items.take(15))
                }
            }
        }
    }

    private val _selectedMovie = mutableStateOf(Movies.MovieDetail())
    val selectedMovie get() = _selectedMovie

    fun selectedMovie(movie: Movies.MovieDetail) {
        _selectedMovie.value = movie
    }
}

data class MovieState(
    var data: List<Movies.MovieDetail> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)