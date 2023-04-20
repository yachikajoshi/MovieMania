package com.yachikajoshi.movielist.ui.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.data.model.Movies


@Composable
fun MovieList(
    modelState: MovieState,
    onMovieClicked: (index: Int) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = modelState.error, block = {
        Toast.makeText(context, modelState.error, Toast.LENGTH_LONG).show()
    })
    if (modelState.isLoading) {
        CircularProgressIndicator()
    } else {
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(modelState.data) { index, movie ->
                MovieItem(
                    movie = movie,
                    Modifier.clickable { onMovieClicked(index) })
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movies.MovieDetail, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.image)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "dec",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(CircleShape)
        )
        Text(text = movie.title)
    }
}

@Preview
@Composable
fun MovieItemPreview() {
    MovieItem(movie = Movies.MovieDetail())
}
