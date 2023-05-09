package com.yachikajoshi.movielist.ui.presentation

sealed class Screen(val route: String) {
    object Dashboard : Screen(route = "dashboard")
    object Search : Screen(route = "search")
    object SeeMore : Screen(route = "see_more")
    object MovieDetail : Screen(route = "movie_detail")
}
