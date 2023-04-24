package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.common.shimmerEffect
import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.ui.theme.Purple200
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@Composable
fun Dashboard(
    navController: NavController,
    modelStateOfTopMovies: MovieState,
    modelStateOfTvShows: MovieState,
    onMovieClicked: (
        movie: Movies.MovieDetail,
        type: MovieType
    ) -> Unit
) {

    Scaffold(
        backgroundColor = Color(0xff21212a),
        topBar = { TopAppBar() },
        bottomBar = { BottomNavigation(navController) },
        content = {
            //Main content
            MainContent(
                paddingValues = it,
                modelStateOfTopMovies = modelStateOfTopMovies,
                modelStateOfTvShows = modelStateOfTvShows,
                onMovieClicked = onMovieClicked
            )
        }
    )
}

@Composable
fun MainContent(
    paddingValues: PaddingValues,
    modelStateOfTopMovies: MovieState,
    modelStateOfTvShows: MovieState,
    onMovieClicked: (
        movie: Movies.MovieDetail,
        type: MovieType
    ) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 10.dp, vertical = 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

        }
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

@Preview
@Composable
fun TopAppBar() {
    TopAppBar(backgroundColor = Color.Transparent,
        title = {},
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_share_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = {
            Row() {
                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                    )
                }
            }
        })
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search
    )
    BottomNavigation(
        backgroundColor = Color(0xff2c2c38),
        modifier = Modifier.clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(icon = {
                Icon(
                    painterResource(id = item.icon),
                    contentDescription = item.title, tint = Color(0xffde6013)
                )
            },
                selectedContentColor = Color(0xffde6013),
                unselectedContentColor = Color(0xFFF5F0E7).copy(0.4f),
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })

        }
    }
}