package com.yachikajoshi.movielist.ui.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

                NavHost(navController = navController, startDestination = Screen.MovieList.route) {
                    composable(route = Screen.MovieList.route) {
                        MovieList(viewModel.movieState.value, onMovieClicked = { index ->
                            navController.navigate(Screen.MovieDetail.route)
                        })
                    }
                    composable(
                        route = Screen.MovieDetail.route
                    ) { entry ->
                        Detail(viewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun Detail(viewModel: MoviesViewModel) {
    Text(text = "helloooooooo")
}


