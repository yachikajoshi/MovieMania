package com.yachikajoshi.movielist.presentation

import com.yachikajoshi.movielist.data.model.Movies

data class MovieState(
    var data: List<Movies.MovieDetail> = emptyList(),
    var error: String = "",
    var isLoading: Boolean = false
)
