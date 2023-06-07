package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.CastResponse
import com.yachikajoshi.movielist.data.model.MovieDetail
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.domain.use_case.CastUseCase
import com.yachikajoshi.movielist.domain.use_case.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieListUseCase: MovieListUseCase,
    private val castUseCase: CastUseCase
) :
    ViewModel() {

    var topRatedMovieState by mutableStateOf(MovieState())
        private set//only viewModel can set the value

    var trendingMovieState by mutableStateOf(MovieState())
        private set

    var upcomingMovieList by mutableStateOf(MovieState())
        private set

    var castState by mutableStateOf(CastState())
        private set

    private var _trailer = MutableStateFlow(TrailerState(MovieTrailer()))
    val trailer = _trailer.asStateFlow()

    private var _selectedMovie = MutableStateFlow(MovieDetailState())
    val selectedMovie = _selectedMovie.asStateFlow()

    private var _suggestedMovieState = MutableStateFlow(MovieState())
    val suggestedMovieState = _suggestedMovieState.asStateFlow()

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

    fun getCast(movieId: String) {
        viewModelScope.launch {
            val response = castUseCase.getCast(movieId = movieId)
            castState = when (response) {
                is Resource.Error -> {
                    CastState(error = response.message ?: "")
                }
                is Resource.Loading -> {
                    CastState(isLoading = true)
                }
                is Resource.Success -> {
                    CastState(data = response.data!!.cast)
                }
            }
        }
    }

    fun getTrailer(movieId: String) {
        viewModelScope.launch {
            _trailer.value = when (val response = movieListUseCase.getTrailer(movieId)) {
                is Resource.Error -> TrailerState(
                    error = response.message ?: "",
                    data = MovieTrailer()
                )
                is Resource.Loading -> TrailerState(isLoading = true, data = MovieTrailer())
                is Resource.Success -> TrailerState(data = response.data!!)
            }
        }
    }

    fun getSuggestedMovies(movieId: String) {
        viewModelScope.launch {
            _suggestedMovieState.value =
                when (val response = movieListUseCase.getSuggestedMovies(movieId = movieId)) {
                    is Resource.Error -> MovieState(error = response.message ?: "")
                    is Resource.Loading -> MovieState(isLoading = true)
                    is Resource.Success -> MovieState(data = response.data!!.results.take(15))
                }
        }
    }

    fun selectedMovie(movieId: String) {
        viewModelScope.launch {
            _selectedMovie.value =
                when (val response = movieListUseCase.getMovieDetail(movieId = movieId)) {
                    is Resource.Error -> MovieDetailState(error = response.message ?: "")
                    is Resource.Loading -> MovieDetailState(isLoading = true)
                    is Resource.Success -> MovieDetailState(data = response.data!!)
                }
        }

    }


    var seeMoreMovieList by mutableStateOf(listOf(MovieResponse.Movie()))
        private set

    fun seeMoreMovieList(movie: List<MovieResponse.Movie>) {
        seeMoreMovieList = movie
    }

}

data class MovieState(
    var data: List<MovieResponse.Movie> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)

data class MovieDetailState(
    var data: MovieDetail? = null,
    var error: String = "",
    var isLoading: Boolean = false
)

data class TrailerState(
    var data: MovieTrailer,
    var error: String = "",
    var isLoading: Boolean = false
)

data class CastState(
    var data: List<CastResponse.Cast> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)