package com.yachikajoshi.movielist.ui.presentation

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.common.Constants
import com.yachikajoshi.movielist.common.Constants.IMAGE_URL
import com.yachikajoshi.movielist.common.getGenreNames
import com.yachikajoshi.movielist.common.getLanguageName
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.ui.theme.Background
import com.yachikajoshi.movielist.ui.theme.TextColor
import com.yachikajoshi.movielist.ui.theme.ViewAllTextColor


@Composable
fun MovieDetailScreen(
    selected: MovieResponse.Movie,
    onBackPressed: () -> Unit,
    viewModel: MoviesViewModel, onMovieClicked: (movieId: String) -> Unit
) {

    var selectedMovie by remember { mutableStateOf(selected) }
    val bookmarks = remember { mutableStateListOf<MovieResponse.Movie>() }
    val scrollState = rememberScrollState()
    val isBookmarked by remember {
        derivedStateOf {
            bookmarks.contains(selectedMovie)
        }
    }

    LaunchedEffect(key1 = selectedMovie) {
        scrollState.animateScrollTo(0)
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
                viewModel = viewModel,
                movie = selectedMovie,
                isBookmarked = isBookmarked,
                onBookmarkChanged = {
                    if (bookmarks.contains(selectedMovie))
                        bookmarks.remove(selectedMovie)
                    else bookmarks.add(selectedMovie)
                },
                onBackPressed = {
                    onBackPressed()
                })
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
                items(viewModel.suggestedMovieState.value.data) { movie ->
                    MovieItems(movie = movie,
                        Modifier.clickable {
                            selectedMovie = movie
                            viewModel.getTrailer(movieId = movie.id)
                        })
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
    viewModel: MoviesViewModel,
    modifier: Modifier = Modifier,
    movie: MovieResponse.Movie,
    isBookmarked: Boolean,
    onBookmarkChanged: () -> Unit,
    onBackPressed: () -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        ExoPlayerView(viewModel = viewModel, movie.poster_path)

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
    movie: MovieResponse.Movie
) {
    val spannedTextGenre = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = ViewAllTextColor
            )
        ) {
            append("Genre : ")
        }
        withStyle(
            style = SpanStyle(
                color = TextColor,
            )
        ) {
            append(movie.genre_ids.getGenreNames())
        }
    }
    val spannedTextLanguage = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = ViewAllTextColor
            )
        ) {
            append("Original Language : ")
        }
        withStyle(
            style = SpanStyle(
                color = TextColor,
            )
        ) {
            append(movie.original_language.getLanguageName())
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        if (movie.media_type == "tv") {
            Text(
                color = Color.White,
                text = movie.name,
                style = MaterialTheme.typography.h5
            )
        } else {
            Text(
                color = Color.White,
                text = movie.title,
                style = MaterialTheme.typography.h5
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.body2,
            color = TextColor
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = spannedTextLanguage,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = spannedTextGenre,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            color = Color(0XFFFFFFFF),
            text = "Suggested Movies",
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
fun ExoPlayerView(viewModel: MoviesViewModel, posterPath: String) {
    val trailerState by viewModel.trailer.collectAsState()
    if (trailerState.data.results.isNotEmpty()) {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val youTubePlayerListener = remember {
            object : AbstractYouTubePlayerListener() {
                var youTubePlayer: YouTubePlayer? = null
                    private set

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    this.youTubePlayer = youTubePlayer
                    if (trailerState.data.results.isNotEmpty()) {
                        youTubePlayer.loadVideo(
                            trailerState.data.results[trailerState.data.results.size - 1].key,
                            0f
                        )
                    }
                }
            }
        }

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)    //0 to disable seekbar
            .build()

        val youTubePlayerView = remember {
            YouTubePlayerView(context).apply {
                enableAutomaticInitialization = false
                initialize(youTubePlayerListener, iFramePlayerOptions)
            }
        }
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {
                youTubePlayerView
            }
        )
        DisposableEffect(key1 = youTubePlayerView, key2 = trailerState, effect = {
            lifecycleOwner.lifecycle.addObserver(youTubePlayerView)
            if (trailerState.data.results.isNotEmpty()) {
                youTubePlayerListener.youTubePlayer?.loadVideo(
                    trailerState.data.results[trailerState.data.results.size - 1].key,
                    0f
                )
            }
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(youTubePlayerView)
            }
        })
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(IMAGE_URL + posterPath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.outline_share_24),
            contentDescription = "dec",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

