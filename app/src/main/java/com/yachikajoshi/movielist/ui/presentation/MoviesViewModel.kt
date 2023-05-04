package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.domain.use_case.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieListUseCase: MovieListUseCase) :
    ViewModel() {

    var topRatedMovieState by mutableStateOf(MovieState())
        private set//only viewModel can set the value

    var trendingMovieState by mutableStateOf(MovieState())
        private set

    var upcomingMovieList by mutableStateOf(MovieState())
        private set

    private var _trailer = MutableStateFlow(TrailerState())
    val trailer = _trailer.asStateFlow()

    init {
        getTop10MovieList()
        getUpcomingMovieList()
        getTrendingMovieList()
    }


    private fun getTop10MovieList() {
        viewModelScope.launch {
            val response = movieListUseCase.getTop10MoviesList()
            topRatedMovieState = when (response) {
                is Resource.Error -> {
                    MovieState(error = response.message ?: "")
                }
                is Resource.Loading -> {
                    MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    MovieState(data = response.data!!.results.take(15))
                }
            }
        }
    }

    private fun getTrendingMovieList() {
        viewModelScope.launch {
            val response = movieListUseCase.getTrendingMovies()
            trendingMovieState = when (response) {
                is Resource.Error -> {
                    MovieState(error = response.message ?: "")
                }
                is Resource.Loading -> {
                    MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    MovieState(data = response.data!!.results)
                }
            }
        }
    }

    private fun getUpcomingMovieList() {
        viewModelScope.launch {
            upcomingMovieList = when (val response = movieListUseCase.getUpcomingMoviesList()) {
                is Resource.Error -> {
                    MovieState(error = response.message ?: "")
                }
                is Resource.Loading -> {
                    MovieState(isLoading = true)
                }
                is Resource.Success -> {
                    MovieState(data = response.data!!.results.take(15))
                }
            }
        }
    }

    fun getTrailer(movieId: String) {
        viewModelScope.launch {
            _trailer.value = when (val response = movieListUseCase.getTrailer(movieId)) {
                is Resource.Error -> TrailerState(
                    error = response.message ?: "",
                    data = emptyList()
                )
                is Resource.Loading -> TrailerState(isLoading = true, data = emptyList())
                is Resource.Success -> TrailerState(data = response.data!!.results)
            }
        }
    }

    var selectedMovie by mutableStateOf(MovieResponse.Movie())
        private set

    fun selectedMovie(movie: MovieResponse.Movie) {
        selectedMovie = movie
    }

}

data class MovieState(
    var data: List<MovieResponse.Movie> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)

data class TrailerState(
    var data: List<MovieTrailer.Trailer> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)