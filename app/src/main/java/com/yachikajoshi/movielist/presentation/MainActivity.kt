package com.yachikajoshi.movielist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yachikajoshi.movielist.Screen
import com.yachikajoshi.movielist.ui.theme.MovieListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieListTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {

    val navController = rememberNavController()

    val viewModel: MoviesViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.MovieList.route) {
        composable(route = Screen.MovieList.route) {
            val movieState = viewModel.movieState.collectAsStateWithLifecycle()
            MovieList(movieState.value, navController)
        }
        composable(
            route = Screen.MovieDetail.route
        ) { entry ->
            Detail(viewModel)
        }
    }
}

@Composable
fun MovieList(
    modelState: MovieState,
    navController: NavController
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = modelState.error, block = {
        Toast.makeText(context, modelState.error, Toast.LENGTH_LONG).show()
    })
    if (modelState.isLoading) {
        CircularProgressIndicator()
    } else {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(modelState.data) {
                MovieItem(
                    movie = it,
                    Modifier.clickable { navController.navigate(Screen.MovieDetail.route) })
            }
        }
    }
}

@Composable
fun Detail(viewModel: MoviesViewModel) {
    Text(text = "helloooooooo")
}


