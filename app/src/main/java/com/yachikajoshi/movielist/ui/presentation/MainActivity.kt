package com.yachikajoshi.movielist.ui.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.yachikajoshi.movielist.ui.theme.MovieListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MoviesViewModel>()
    private val allMoviesViewModel by viewModels<AllMoviesViewModel>()

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
                            modelStateOfUpcomingMovies = viewModel.upcomingMovieList,
                            onMovieClicked = { selectedMovie ->
                                viewModel.selectedMovie(movieId = selectedMovie.id)
                                viewModel.getTrailer(movieId = selectedMovie.id)
                                viewModel.getSuggestedMovies(selectedMovie.id)
                                navController.navigate(Screen.MovieDetail.route)
                            },
                            onClickTrendingSeeMore = {
                                navController.navigate(Screen.SeeMore.route + "/trending")
                            }, onClickTopRatedSeeMore = {
                                navController.navigate(Screen.SeeMore.route + "/topRated")
                            }
                        )
                    }
                    composable(route = Screen.Search.route) {
                        Search(
                            searchViewModel = allMoviesViewModel,
                            onBackPressed = {
                                navController.navigateUp()
                            },
                        onMovieClicked = {selectedMovie->

                            viewModel.selectedMovie(movieId = selectedMovie.id)
                            viewModel.getSuggestedMovies(selectedMovie.id)
                            navController.navigate(Screen.MovieDetail.route)
                        })
                    }
                    composable(
                        route = Screen.SeeMore.route + "/{see_more}",
                        arguments = listOf(navArgument(name = "see_more") {
                            type = NavType.StringType
                        })

                    ) { backStackEntry ->
                        val navigateTo = backStackEntry.arguments?.getString("see_more")
                        val movies = when (navigateTo) {
                            "trending" -> {
                                allMoviesViewModel.moviePager.collectAsLazyPagingItems()
                            }
                            "topRated" -> {
                                allMoviesViewModel.topRatedMoviePager.collectAsLazyPagingItems()
                            }
                            else -> {
                                allMoviesViewModel.moviePager.collectAsLazyPagingItems()
                            }
                        }
                        SeeMoreItem(
                            movies = movies,
                            onMovieClicked = { selectedMovie ->
                                viewModel.selectedMovie(movieId = selectedMovie.id)
                                viewModel.getSuggestedMovies(selectedMovie.id)
                                navController.navigate(Screen.MovieDetail.route)
                            }
                        ) {
                            navController.navigateUp()
                        }
                    }
                    composable(
                        route = Screen.MovieDetail.route,
                    ) { entry ->
                        val selectedMovie = viewModel.selectedMovie.collectAsState().value.data
                        selectedMovie?.let {
                            MovieDetailScreen(
                                viewModel = viewModel,
                                selected = it,
                                onBackPressed = {
                                    navController.navigateUp()
                                }
                            )
                        }
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
    }
}
