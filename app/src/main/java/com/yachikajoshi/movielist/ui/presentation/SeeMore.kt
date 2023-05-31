package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.ui.theme.ReadColor

@Composable
fun SeeMoreItem(
    movies: LazyPagingItems<MovieResponse.Movie>,
    onMovieClicked: (movie: MovieResponse.Movie) -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff21212a)),
    ) {
        IconButton(onClick = { onBackPressed() }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "", tint = Color.White
            )
        }
        Box {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                contentPadding = PaddingValues(
                    horizontal = 10.dp, vertical = 10.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(movies.itemCount) { index ->
                    MovieItems(
                        movie = movies[index]!!,
                        modifier = Modifier.clickable { onMovieClicked(movies[index]!!) })
                }
            }
        }
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = ReadColor
            )
        }
    }
}