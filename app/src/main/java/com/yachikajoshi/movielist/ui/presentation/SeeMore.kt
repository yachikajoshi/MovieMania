package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.data.model.MovieResponse

@Composable
fun SeeMoreItem(
    movieList: List<MovieResponse.Movie>,
    onMovieClicked: (movie: MovieResponse.Movie) -> Unit, onBackPressed: () -> Unit
) {
    Column(modifier = Modifier.background(Color(0xff21212a))) {
        IconButton(onClick = { onBackPressed() }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "", tint = Color.White
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(
                horizontal = 10.dp, vertical = 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(movieList) { movie ->
                MovieItems(movie = movie, modifier = Modifier.clickable { onMovieClicked(movie) })
            }
        }
    }

}