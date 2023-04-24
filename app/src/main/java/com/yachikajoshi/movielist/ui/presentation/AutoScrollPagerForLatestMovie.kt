package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.yachikajoshi.movielist.ui.theme.Purple200
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LatestMovies(movieState: MovieState) {
    val pagerState = rememberPagerState(initialPage = 0)
    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(1000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % movieState.data.size,
                animationSpec = tween(durationMillis = 600)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Column(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(color = Purple200),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        }
        Spacer(modifier = Modifier.height(30.dp))
        HorizontalPager(
            pageCount = movieState.data.size, state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) { page ->
            Card(
                backgroundColor = Color.Gray.copy(alpha = 0.4f),
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp,
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset =
                            calculateCurrentOffsetForPage(pagerState, page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 0.2f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .fillMaxWidth()
                    .padding(vertical = 25.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movieState.data[page].image)
                        .crossfade(true)
                        .scale(Scale.FILL)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .offset {
                            // Calculate the offset for the current page from the
                            // scroll position
                            val pageOffset =
                                calculateCurrentOffsetForPage(pagerState, page).absoluteValue
                            // Then use it as a multiplier to apply an offset
                            IntOffset(
                                x = (70.dp * pageOffset).roundToPx(),
                                y = 0,
                            )
                        }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun calculateCurrentOffsetForPage(
    pagerState: PagerState,
    page: Int
): Float {
    val currentIndex = pagerState.currentPage
    val currentPageOffset = pagerState.currentPageOffsetFraction

    return when (page) {
        currentIndex -> {
            currentPageOffset.absoluteValue
        }
        currentIndex - 1 -> {
            1 + currentPageOffset.coerceAtMost(0f)
        }
        currentIndex + 1 -> {
            1 - currentPageOffset.coerceAtLeast(0f)
        }
        else -> {
            1f
        }
    }
}