package com.yachikajoshi.movielist.ui.presentation

import com.yachikajoshi.movielist.R

sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomNavItem("Home", R.drawable.round_home_24, "dashboard")
    object Search : BottomNavItem("Search", R.drawable.round_home_24, "movie_detail")
}
