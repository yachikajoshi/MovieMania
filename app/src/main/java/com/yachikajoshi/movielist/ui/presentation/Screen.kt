package com.yachikajoshi.movielist.ui.presentation

sealed class Screen(val route: String) {
    object Dashboard : Screen(route = "dashboard")
    object MovieDetail : Screen(route = "movie_detail")
}
