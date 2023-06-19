package com.yachikajoshi.movielist.ui.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.common.Constants.IMAGE_URL
import com.yachikajoshi.movielist.common.getDuration
import com.yachikajoshi.movielist.data.model.MovieDetail
import com.yachikajoshi.movielist.ui.theme.*


@Composable
fun MovieDetailScreen(
    selected: MovieDetail,
    onBackPressed: () -> Unit,
    viewModel: MoviesViewModel
) {

    val context = LocalContext.current
    var selectedMovie by remember { mutableStateOf(selected) }
    val bookmarks = remember { mutableStateListOf<MovieDetail>() }
    val scrollState = rememberScrollState()
    var showSimilarMovieTitle by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = viewModel.selectedMovie) {
        viewModel.selectedMovie.collect { movieDetailState ->
            movieDetailState.data?.let { movieDetail ->
                selectedMovie = movieDetail
                scrollState.animateScrollTo(0)
                viewModel.getCast(selectedMovie.id.toString())
            }
        }
    }
    val isBookmarked by remember {
        derivedStateOf {
            bookmarks.contains(selectedMovie)
        }
    }
    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(it)
                .background(
                    Background
                )
        ) {
            MovieHeader(
                movie = selectedMovie,
                isBookmarked = isBookmarked,
                onBookmarkChanged = {
                    if (bookmarks.contains(selectedMovie))
                        bookmarks.remove(selectedMovie)
                    else bookmarks.add(selectedMovie)
                },
                onBackPressed = {
                    onBackPressed()
                },
                onPlayTrailer = {
                    val trailer = viewModel.trailer.value.data.results.find { it.type == "Trailer" }
                    val appIntent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("vnd.youtube:${trailer!!.key}")
                        )
                    context.startActivity(appIntent)
                })
            MovieDescription(
                movie = selectedMovie
            )
            MovieCast(castList = viewModel.castState.data)
            Spacer(modifier = Modifier.height(6.dp))
            if (showSimilarMovieTitle) {
                Text(
                    color = Color(0XFFFFFFFF),
                    text = "Similar Movies",
                    style = MaterialTheme.typography.h1,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if (viewModel.suggestedMovieState.value.data.isEmpty()) {
                    showSimilarMovieTitle = false
                }
                items(viewModel.suggestedMovieState.value.data.filter { data -> data.poster_path != null }) { movie ->
                    MovieItems(movie = movie,
                        Modifier.clickable {
                            viewModel.selectedMovie(movie.id)
                            viewModel.getTrailer(movie.id)
                        })
                }
            }
        }
    }
}

@Composable
fun MovieHeader(
    modifier: Modifier = Modifier,
    movie: MovieDetail,
    isBookmarked: Boolean,
    onBookmarkChanged: () -> Unit,
    onBackPressed: () -> Unit,
    onPlayTrailer: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Box {
            Poster(posterPath = movie.poster_path)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Background,
                            ),
                            startY = 0.0f,
                            endY = 1560f
                        )
                    )
            )
            PlayTrailer(
                onPlayTrailer,
                movie,
                onBookmarkChanged,
                isBookmarked,
                Modifier.align(Alignment.BottomCenter)
            )
        }

        TopAppBar(
            elevation = 0.dp,
            modifier = modifier,
            title = {},
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "", tint = Color.White
                    )
                }
            },
            backgroundColor = Color.Transparent
        )
    }

}

@Composable
fun PlayTrailer(
    onPlayTrailer: () -> Unit,
    movie: MovieDetail,
    onBookmarkChanged: () -> Unit,
    isBookmarked: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onPlayTrailer()
                    }
            )
            Text(
                text = " Trailer",
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_star_rate_24),
                contentDescription = null, modifier = Modifier.size(16.dp)
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = movie.vote_average.toString().take(3),
                style = MaterialTheme.typography.h5,
                color = Color.White
            )
            Text(
                text = " | ${movie.vote_count}",
                style = MaterialTheme.typography.caption,
                color = IconColorOnDarkScreen
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(
                    id = R.drawable.outline_share_24
                ),
                contentDescription = null,
                tint = IconColorOnDarkScreen,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp)
            )

            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onBookmarkChanged() },
                painter = if (isBookmarked) painterResource(id = R.drawable.baseline_bookmark_24) else painterResource(
                    id = R.drawable.outline_bookmark_border_24
                ),
                contentDescription = null,
                tint = IconColorOnDarkScreen
            )
        }
    }
}

@Composable
fun MovieDescription(
    modifier: Modifier = Modifier,
    movie: MovieDetail
) {
    val spannedTextGenre = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = BodyColor,
            )
        ) {
            append(movie.runtime.getDuration())
        }
        withStyle(
            style = SpanStyle(
                color = BodyColor,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(" • ")
        }
        withStyle(
            style = SpanStyle(
                color = BodyColor,
            )
        ) {
            val name = StringBuilder()
            movie.genres.forEach {
                name.append("${it.name}, ")
            }
            append(name.toString().dropLast(2))
        }
        withStyle(
            style = SpanStyle(
                color = BodyColor,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(" • ")
        }
        withStyle(
            style = SpanStyle(
                color = BodyColor,
                fontWeight = FontWeight.Normal,
                fontFamily = OpenSans,
                fontSize = 14.sp
            )
        ) {
            append(movie.release_date.take(4))
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        //Title of movie
        Text(
            color = Color.White,
            text = movie.title,
            style = MaterialTheme.typography.h1
        )
        Spacer(modifier = Modifier.height(6.dp))
        //Genre, Duration & Year
        Text(
            text = spannedTextGenre
        )
        Spacer(modifier = Modifier.height(15.dp))
        //Overview
        ExpandingText(text = movie.overview)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Top Cast",
            style = MaterialTheme.typography.h1,
            color = Color.White
        )
    }
}

@Composable
fun Poster(posterPath: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(IMAGE_URL + posterPath)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.68f)
    )
}

