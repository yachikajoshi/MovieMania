package com.yachikajoshi.movielist.ui.presentation

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yachikajoshi.movielist.common.Constants.IMAGE_URL
import com.yachikajoshi.movielist.data.model.MovieResponse
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlin.math.absoluteValue

@OptIn(ExperimentalSnapperApi::class, ExperimentalFoundationApi::class)
@Composable
fun ScrollImage(movieState: MovieState) {
    val movie: List<MovieResponse.Movie> = movieState.data.take(4)
    val pagerState = rememberPagerState()
    val listState = rememberLazyListState()
    var currentIndex: Int by remember {
        mutableStateOf(-1)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier) {
        HorizontalPager(
            pageCount = movie.size,
            state = pagerState,
//            flingBehavior = rememberSnapperFlingBehavior(pagerState)
        ) { page ->
            currentIndex = pagerState.currentPage
            LatestMovies1(movie, page)
        }
//        LazyRow(
//            state = listState,
//            flingBehavior = rememberSnapperFlingBehavior(listState),
//        ) {
//            items(movie.size) {
//                currentIndex = it
//                LatestMovies1(movie, it)
//            }
//        }
        if (currentIndex != -1) {
            Spacer(modifier = Modifier.padding(top = 20.dp))
            Text(
                modifier = Modifier.padding(horizontal = 30.dp),
                text = movieState.data[currentIndex].title,
                style = MaterialTheme.typography.h1,
                color = Color.White
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            PageIndicator(numberOfPages = 4, selectedPage = currentIndex)
        }
    }
}

@Composable
fun LatestMovies1(movie: List<MovieResponse.Movie>, page: Int) {
    Column(
        modifier = Modifier.padding(horizontal = 15.dp),
    ) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(IMAGE_URL + movie[page].poster_path)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
//                modifier = Modifier
//                    .aspectRatio(3.0f)
            )
        }
    }

}

@Composable
fun PageIndicator(
    numberOfPages: Int,
    modifier: Modifier = Modifier,
    selectedPage: Int = 0,
    selectedColor: Color = Color(0xFF3E6383),
    defaultColor: Color = Color.LightGray,
    defaultRadius: Dp = 5.dp,
    selectedLength: Dp = 10.dp,
    space: Dp = 10.dp,
    animationDurationInMillis: Int = 300,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == selectedPage
            PageIndicatorView(
                isSelected = isSelected,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
                animationDurationInMillis = animationDurationInMillis,
            )
        }
    }
}

@Composable
fun PageIndicatorView(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    animationDurationInMillis: Int,
    modifier: Modifier = Modifier,
) {
    val color: Color by animateColorAsState(
        targetValue = if (isSelected) {
            selectedColor
        } else {
            defaultColor
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )
    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            selectedLength
        } else {
            defaultRadius
        },
        animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )

    Canvas(
        modifier = modifier
            .size(
                width = width,
                height = defaultRadius,
            ),
    ) {
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(
                width = width.toPx(),
                height = defaultRadius.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = defaultRadius.toPx(),
                y = defaultRadius.toPx(),
            ),
        )
    }
}