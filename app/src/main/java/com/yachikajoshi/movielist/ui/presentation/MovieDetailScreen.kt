package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.ui.theme.DarkPurple
import com.yachikajoshi.movielist.ui.theme.MediumPurple

@Composable
fun MovieDetailScreen(
    selected: Movies.MovieDetail,
    listOfMovies: List<Movies.MovieDetail>,
    onBackPressed: () -> Unit
) {
    var selectedMovie by remember { mutableStateOf(selected) }
    val bookmarks = remember { mutableStateListOf<Movies.MovieDetail>() }
    val scrollState = rememberScrollState()

    val isBookmarked by remember {
        derivedStateOf {
            bookmarks.contains(selectedMovie)
        }
    }

    val moviesList by remember {
        derivedStateOf {
            listOfMovies.filter { it != selectedMovie }
        }
    }

    LaunchedEffect(key1 = selectedMovie) {
        scrollState.animateScrollTo(0)
    }

    Scaffold() {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(it)
                .background(
                    Brush.verticalGradient(
                        listOf(MediumPurple, DarkPurple)
                    )
                )
        ) {
            MovieHeader(movie = selectedMovie, isBookmarked = isBookmarked, onBookmarkChanged = {
                if (bookmarks.contains(selectedMovie))
                    bookmarks.remove(selectedMovie)
                else bookmarks.add(selectedMovie)
            })
            PlaySection(
                isBookmarked = isBookmarked,
                movieName = selectedMovie.fullTitle,
                onBookmarkChanged = {
                    if (bookmarks.contains(selectedMovie))
                        bookmarks.remove(selectedMovie)
                    else bookmarks.add(selectedMovie)
                }
            )
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            MovieDescription(
                modifier = Modifier.padding(horizontal = 16.dp),
                movie = selectedMovie
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(moviesList) { movie ->
                    MovieItem(movie = movie) {
                        selectedMovie = it
                    }
                }
            }
        }
    }
}

@Composable
fun PlaySection(
    modifier: Modifier = Modifier,
    isBookmarked: Boolean,
    movieName: String,
    onBookmarkChanged: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = onBookmarkChanged
                ) {
                    Icon(
                        painter = if (isBookmarked) painterResource(id = R.drawable.baseline_bookmark_24) else painterResource(
                            id = R.drawable.outline_bookmark_border_24
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                Text(
                    text = "My List",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption
                )
            }
            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
            ) {
                Icon(
                    modifier = Modifier.size(42.dp),
                    painter = painterResource(id = R.drawable.baseline_play_arrow_24),
                    contentDescription = null
                )
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = onBookmarkChanged
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.outline_share_24
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }
                Text(
                    text = "Share",
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.caption
                )
            }

        }
        Column(
            modifier = modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Available now",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Watch $movieName",
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.subtitle1
            )
        }
    }


}

@Composable
fun MovieHeader(
    modifier: Modifier = Modifier,
    movie: Movies.MovieDetail,
    isBookmarked: Boolean,
    onBookmarkChanged: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = movie.image,
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(), contentScale = ContentScale.Fit
        )
        TopAppBar(elevation = 0.dp, modifier = modifier, title = {}, navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "", tint = Color.White
                )
            }
        }, backgroundColor = Color.Transparent, actions = {
            IconButton(
                onClick = onBookmarkChanged
            ) {
                Icon(
                    painter = if (isBookmarked) painterResource(id = R.drawable.baseline_bookmark_24) else painterResource(
                        id = R.drawable.outline_bookmark_border_24
                    ),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        })
    }

}

@Composable
fun MovieDescription(
    modifier: Modifier = Modifier,
    movie: Movies.MovieDetail
) {
    val spannedTextGenres = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.secondary,
            )
        ) {
            append("Genres : ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.onSurface,
            )
        ) {
            append(movie.genres)
        }
    }
    val spannedTextCrew = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.secondary,
            )
        ) {
            append("Crew : ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.onSurface,
            )
        ) {
            append(movie.crew)
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "The Plot",
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.75f),
            text = movie.imDbRating,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(24.dp))
        if (movie.genres.isNotEmpty()) {
            Text(
                text = spannedTextGenres,
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = spannedTextCrew,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            color = MaterialTheme.colors.onSurface,
            text = "Suggested Movies",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movies.MovieDetail,
    onClicked: (Movies.MovieDetail) -> Unit
) {
    AsyncImage(
        model = movie.image,
        contentDescription = null,
        modifier = modifier
            .height(180.dp)
            .width(132.dp)
            .clip(RoundedCornerShape(5))
            .clickable { onClicked(movie) },
        contentScale = ContentScale.Crop
    )
}
