package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.common.Constants
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.ui.theme.TextColor

@Composable
fun MovieItems(movie: MovieResponse.Movie, modifier: Modifier = Modifier) {
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
            .border(width = 1.dp, color = TextColor, shape = RoundedCornerShape(5))
    )
}


