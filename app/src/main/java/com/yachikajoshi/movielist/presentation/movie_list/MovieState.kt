package com.yachikajoshi.movielist.presentation.movie_list

import com.yachikajoshi.movielist.data.model.Movies

data class MovieState(
    var data: List<Movies.MovieDetail>? = null,
    var error: String = "",
    var isLoading: Boolean = false
)
