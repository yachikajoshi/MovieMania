package com.yachikajoshi.movielist.ui.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.ui.theme.MovieListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MovieListTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
                    composable(route = Screen.Dashboard.route) {
                        Dashboard(navController = navController,
                            modelStateOfTrendingMovies = viewModel.trendingMovieState,
                            modelStateOfTopMovies = viewModel.topRatedMovieState,
                            modelStateOfTvShows = viewModel.upcomingMovieList,
                            onMovieClicked = { selectedMovie, type ->
                                viewModel.selectedMovie(movie = selectedMovie)
                                viewModel.getTrailer(movieId = selectedMovie.id)
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
                        val type = entry.arguments!!.getString("movie_type") ?: ""
                        var listOfMovies: List<MovieResponse.Movie> = listOf()
                        if (type == "TOP_MOVIES") {
                            listOfMovies = viewModel.topRatedMovieState.data
                        } else if (type == "TV_SHOWS") {
                            listOfMovies = viewModel.upcomingMovieList.data
                        }
                        val selectedMovie = viewModel.selectedMovie
                        MovieDetailScreen(
                            viewModel = viewModel,
                            selected = selectedMovie,
                            listOfMovies = listOfMovies,
                            onBackPressed = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
        hideSystemUI()
    }

    fun hideSystemUI() {
        //Hides the ugly action bar at the top
        actionBar?.hide()

        //Hide the status bars
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        } else {
//            window.insetsController?.apply {
//                hide(WindowInsets.Type.statusBars())
//                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//            }
//        }
    }
}
