package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yachikajoshi.movielist.common.Resource
import com.yachikajoshi.movielist.data.model.MovieTrailer
import com.yachikajoshi.movielist.data.model.UpcomingMovies
import com.yachikajoshi.movielist.domain.use_case.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val movieListUseCase: MovieListUseCase) :
    ViewModel() {

    private var _movieState = mutableStateOf(MovieState())
    val movieState get() = _movieState


    private val _tvShows = mutableStateOf(MovieState())
    val tvShows get() = _tvShows

    private val _upcomingMovieList = mutableStateOf(MovieState())
    val upcomingMovieList get() = _upcomingMovieList

    private val _trailer = mutableStateOf(TrailerState())
    val trailer get() = _trailer

    init {
        getTop10MovieList()
        getUpcomingMovieList()
//        getTopTVShows()
    }


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
                        MovieState(data = response.data!!.results.take(15))
                }
            }
        }
    }

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
                        MovieState(data = response.data!!.results.take(15))
                }
            }
        }
    }

    private fun getTrailer(movieId: String) {
        viewModelScope.launch {
            when (val response = movieListUseCase.getTrailer(movieId)) {
                is Resource.Error -> _trailer.value = TrailerState(error = response.message ?: "")
                is Resource.Loading -> _trailer.value = TrailerState(isLoading = true)
                is Resource.Success -> _trailer.value =
                    TrailerState(data = response.data!!.results)
            }
        }
    }

//    private fun getTopTVShows() {
//        viewModelScope.launch {
//            when (val response = movieListUseCase.getTopTVShows()) {
//                is Resource.Error -> {
//                    _tvShows.value = MovieState(error = response.message ?: "")
//                }
//                is Resource.Loading -> {
//                    _tvShows.value = MovieState(isLoading = true)
//                }
//                is Resource.Success -> {
//                    _tvShows.value =
//                        MovieState(data = response.data!!.results.take(15))
//                }
//            }
//        }
//    }

    private val _selectedMovie = mutableStateOf(UpcomingMovies.Movie())
    val selectedMovie get() = _selectedMovie

    fun selectedMovie(movie: UpcomingMovies.Movie) {
        _selectedMovie.value = movie
    }
}

data class MovieState(
    var data: List<UpcomingMovies.Movie> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)

data class TrailerState(
    var data: List<MovieTrailer.Trailer> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)