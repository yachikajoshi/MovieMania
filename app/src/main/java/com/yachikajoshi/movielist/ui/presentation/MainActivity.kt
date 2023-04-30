package com.yachikajoshi.movielist.ui.presentation

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yachikajoshi.movielist.data.model.UpcomingMovies
import com.yachikajoshi.movielist.ui.theme.MovieListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MovieListTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<MoviesViewModel>()
                NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
                    composable(route = Screen.Dashboard.route) {
                        Dashboard(navController, viewModel.movieState.value,
                            viewModel.upcomingMovieList.value,
                            onMovieClicked = { selectedMovie, type ->
                                viewModel.selectedMovie(movie = selectedMovie)
                                navController.navigate(Screen.MovieDetail.route + "/" + type)
                            })
                    }
                    composable(route = Screen.Search.route) {
                        Search(onBackPressed = {
                            navController.navigateUp()
                        })
                    }
                    composable(
                        route = Screen.MovieDetail.route + "/{movie_type}",
                        arguments = listOf(navArgument("movie_type")
                        {
                            type = NavType.StringType
                        })
                    ) { entry ->
                        val selectedMovie = viewModel.selectedMovie.value
                        val type = entry.arguments!!.getString("movie_type") ?: ""
                        var listOfMovies: List<UpcomingMovies.Movie> = listOf()
                        if (type == "TOP_MOVIES") {
                            listOfMovies = viewModel.movieState.value.data
                        } else if (type == "TV_SHOWS") {
                            listOfMovies = viewModel.upcomingMovieList.value.data
                        }
                        MovieDetailScreen(
                            selected = selectedMovie,
                            listOfMovies = listOfMovies,
                            onBackPressed = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
//        hideSystemUI()
    }

    fun hideSystemUI() {
        //Hides the ugly action bar at the top
        actionBar?.hide()

        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}

