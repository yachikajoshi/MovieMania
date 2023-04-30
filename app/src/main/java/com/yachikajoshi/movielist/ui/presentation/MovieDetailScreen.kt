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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.common.Constants
import com.yachikajoshi.movielist.common.Constants.IMAGE_URL
import com.yachikajoshi.movielist.data.model.UpcomingMovies
import com.yachikajoshi.movielist.ui.theme.*

@Composable
fun MovieDetailScreen(
    selected: UpcomingMovies.Movie,
    listOfMovies: List<UpcomingMovies.Movie>,
    onBackPressed: () -> Unit
) {
    var selectedMovie by remember { mutableStateOf(selected) }
    val bookmarks = remember { mutableStateListOf<UpcomingMovies.Movie>() }
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
                    Background
                )
        ) {
            MovieHeader(movie = selectedMovie, isBookmarked = isBookmarked, onBookmarkChanged = {
                if (bookmarks.contains(selectedMovie))
                    bookmarks.remove(selectedMovie)
                else bookmarks.add(selectedMovie)
            }, onBackPressed = { onBackPressed() })
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            MovieDescription(
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

@Preview
@Composable
fun PlaySection(
    modifier: Modifier = Modifier,
    movieName: String = "Hello"
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
    movie: UpcomingMovies.Movie,
    isBookmarked: Boolean,
    onBookmarkChanged: () -> Unit,
    onBackPressed: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = Constants.IMAGE_URL + movie.poster_path,
            contentDescription = null,
            modifier = modifier
                .fillMaxSize(), contentScale = ContentScale.FillWidth
        )
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
            backgroundColor = Color.Transparent,
            actions = {
                Row() {
                    IconButton(
                        onClick = onBookmarkChanged
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.outline_share_24
                            ),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    IconButton(
                        onClick = onBookmarkChanged
                    ) {
                        Icon(
                            painter = if (isBookmarked) painterResource(id = R.drawable.baseline_bookmark_24) else painterResource(
                                id = R.drawable.outline_bookmark_border_24
                            ),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        )
    }

}

@Composable
fun MovieDescription(
    modifier: Modifier = Modifier,
    movie: UpcomingMovies.Movie
) {
    val spannedTextGenres = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = ViewAllTextColor,
            )
        ) {
            append("Genres : ")
        }
        withStyle(
            style = SpanStyle(
                color = Color(0XFFFFFFFF),
            )
        ) {
//            append(movie.genre_ids.forEach {  })
        }
    }
    val spannedTextCrew = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = ViewAllTextColor,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Crew : ")
        }
        withStyle(
            style = SpanStyle(
                color = Color(0XFFFFFFFF),
            )
        ) {
//            append(movie.crew)
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            color = Color.White,
            text = movie.original_title,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(10.dp))

//        if (movie.genres.isNotEmpty()) {
//            Text(
//                text = spannedTextGenres,
//                style = MaterialTheme.typography.body2
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//        }
        Text(
            text = spannedTextCrew,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            color = Color(0XFFFFFFFF),
            text = "Suggested Movies",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: UpcomingMovies.Movie,
    onClicked: (UpcomingMovies.Movie) -> Unit
) {
    AsyncImage(
        model = IMAGE_URL+movie.poster_path,
        contentDescription = null,
        modifier = modifier
            .height(180.dp)
            .width(132.dp)
            .clip(RoundedCornerShape(5))
            .clickable { onClicked(movie) },
        contentScale = ContentScale.Crop
    )
}
