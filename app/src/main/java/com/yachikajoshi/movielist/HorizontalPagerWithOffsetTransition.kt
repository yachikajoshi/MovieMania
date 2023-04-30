package com.yachikajoshi.movielist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yachikajoshi.movielist.common.shimmerEffect
import kotlin.math.absoluteValue

val listPicture = listOf(
    "https://picsum.photos/id/237/200/300",
    "https://picsum.photos/seed/picsum/200/300",
    "https://picsum.photos/200/300/?blur\n",
    "https://picsum.photos/200/300?grayscale",
    "https://picsum.photos/200/300?grayscale",
    "https://picsum.photos/200/300?grayscale",
    "https://picsum.photos/200/300?grayscale",
    "https://picsum.photos/200/300?grayscale"
)

// extension method for current page offset
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithOffsetTransition() {
    val pagerState = rememberPagerState()
    HorizontalPager(
        pageCount = listPicture.size,
        pageSize = threePagesPerViewport,
        beyondBoundsPageCount = 2,
        modifier = Modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
        //todo below code is not working
//        Box(
//            Modifier
//                .graphicsLayer {
////                val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
////                // translate the contents by the size of the page, to prevent the pages from sliding in from left or right and stays in the center
////                translationX = pageOffset * size.width
////                // apply an alpha to fade the current page in and the old page out
////                alpha = 1 - pageOffset.absoluteValue
//                    val pageOffset = (
//                            (pagerState.currentPage - page) + pagerState
//                                .currentPageOffsetFraction
//                            ).absoluteValue
//
//                    // We animate the alpha, between 50% and 100%
//                    alpha = lerp(
//                        start = 0.5f,
//                        stop = 1f,
//                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
//                    )
//                }
//                .fillMaxSize()) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(listPicture[page])
//                    .crossfade(true)
//                    .build(),
//                placeholder = painterResource(R.drawable.ic_launcher_background),
//                contentDescription = "dec",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//                    .clip(RoundedCornerShape(16))
//            )
//        }
        //todo song animation
        Box(modifier = Modifier.fillMaxSize()) {
            // Contains Image and Text composables
            SongInformationCard(
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.Center),
                pagerState = pagerState,
                page = page
            )
        }
    }

    fun Modifier.pagerFadeTransition(page: Int, pagerState: PagerState) =
        graphicsLayer {
            val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
            translationX = pageOffset * size.width
            alpha = 1 - pageOffset.absoluteValue
        }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SongInformationCard(
    pagerState: PagerState,
    page: Int,
    modifier: Modifier = Modifier
) {
    Card(

    ) {
        Column() {
            val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
            Image(
                modifier = Modifier
                    /* other modifiers */
                    .graphicsLayer {
                        // get a scale value between 1 and 1.75f, 1.75 will be when its resting,
                        // 1f is the smallest it'll be when not the focused page
                        val scale = lerp(1f, 1.75f, pageOffset)
                        // apply the scale equally to both X and Y, to not distort the image
                        scaleX = scale
                        scaleY = scale
                    }, contentDescription = "",
                painter = painterResource(id = R.drawable.ic_launcher_background)
            )
        }
    }
}

@ExperimentalFoundationApi
private val threePagesPerViewport = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return (availableSpace - 2 * pageSpacing) / 3
    }
}