package com.yachikajoshi.movielist.ui.presentation

sealed class Screen(val route: String) {
    object MovieList : Screen(route = "movie_list")
    object MovieDetail : Screen(route = "movie_detail")

    fun withArgs(vararg args: MovieDetail): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}
