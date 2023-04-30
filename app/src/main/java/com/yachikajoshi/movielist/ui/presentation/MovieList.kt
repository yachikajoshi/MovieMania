package com.yachikajoshi.movielist.ui.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.common.Constants
import com.yachikajoshi.movielist.common.shimmerEffect
import com.yachikajoshi.movielist.data.model.UpcomingMovies
import com.yachikajoshi.movielist.ui.theme.DarkPurple
import com.yachikajoshi.movielist.ui.theme.MediumPurple


@Composable
fun MovieList(
    modelStateOfTopMovies: MovieState,
    modelStateOfTvShows: MovieState,
    onMovieClicked: (movie: UpcomingMovies.Movie, type: MovieType) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = modelStateOfTopMovies.error, block = {
        Toast.makeText(context, modelStateOfTopMovies.error, Toast.LENGTH_LONG).show()
    })

    Column(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(MediumPurple, DarkPurple)
                )
            )
    ) {
        Text(
            text = "Top Movies",
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.poppins_semi_bold),
                color = Color(0XFFFFFFFF)
            )
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 10.dp, vertical = 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (modelStateOfTopMovies.isLoading) {
                items(5) { int ->
                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .width(132.dp)
                            .clip(RoundedCornerShape(5))
                            .shimmerEffect()
                    )
                }
            } else {
                itemsIndexed(modelStateOfTopMovies.data) { index, movie ->
                    MovieItems(
                        movie = movie,
                        Modifier.clickable { onMovieClicked(movie, MovieType.TOP_MOVIES) })
                }
            }

        }
        Text(
            text = "Top TV Shows",
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.poppins_semi_bold),
                color = Color.White
            )
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (modelStateOfTopMovies.isLoading) {
                items(5) { int ->
                    Box(
                        modifier = Modifier
                            .height(180.dp)
                            .width(132.dp)
                            .clip(RoundedCornerShape(5))
                            .shimmerEffect()
                    )
                }
            } else {
                itemsIndexed(modelStateOfTvShows.data) { index, movie ->
                    MovieItems(
                        movie = movie,
                        Modifier.clickable { onMovieClicked(movie, MovieType.TV_SHOWS) })
                }
            }
        }
    }

}

@Composable
fun MovieItems(movie: UpcomingMovies.Movie, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(Constants.IMAGE_URL + movie.poster_path)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.outline_share_24),
        contentDescription = "dec",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(180.dp)
            .width(132.dp)
            .clip(RoundedCornerShape(5))
    )
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
}

enum class MovieType {
    TOP_MOVIES, TV_SHOWS, UPCOMING_MOVIES
}


