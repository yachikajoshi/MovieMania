package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yachikajoshi.movielist.R
import com.yachikajoshi.movielist.common.shimmerEffect
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.ui.theme.BottomAppBarColor
import com.yachikajoshi.movielist.ui.theme.ViewAllTextColor

@Composable
fun Dashboard(
    navController: NavController,
    modelStateOfTrendingMovies: MovieState,
    modelStateOfTopMovies: MovieState,
    modelStateOfTvShows: MovieState,
    onMovieClicked: (
        movie: MovieResponse.Movie
    ) -> Unit, onClickTrendingSeeMore: () -> Unit,
    onClickTopRatedSeeMore: () -> Unit
) {

    Scaffold(
        backgroundColor = Color(0xff21212a),
        bottomBar = { BottomNavigation(navController) },
        content = {
            MainContent(
                paddingValues = it,
                modelStateOfTopMovies = modelStateOfTopMovies,
                modelStateOfTrendingMovies = modelStateOfTrendingMovies,
                modelStateOfTvShows = modelStateOfTvShows,
                onMovieClicked = onMovieClicked,
                onClickTrendingSeeMore = onClickTrendingSeeMore,
                onClickTopRatedSeeMore = onClickTopRatedSeeMore
            )
        }
    )
}

@Composable
fun MainContent(
    paddingValues: PaddingValues,
    modelStateOfTopMovies: MovieState,
    modelStateOfTrendingMovies: MovieState,
    modelStateOfTvShows: MovieState,
    onMovieClicked: (
        movie: MovieResponse.Movie
    ) -> Unit, onClickTrendingSeeMore: () -> Unit,
    onClickTopRatedSeeMore: () -> Unit
) {
    Box {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            LatestMovies(modelStateOfTopMovies)
            TrendingMovies(modelStateOfTrendingMovies, onMovieClicked, onClickTrendingSeeMore)
            Spacer(modifier = Modifier.padding(10.dp))
            TopRatedMovies(modelStateOfTopMovies, onMovieClicked, onClickTopRatedSeeMore)

        }
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
                Row {
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

}

@Composable
fun TopRatedMovies(
    modelStateOfTopMovies: MovieState,
    onMovieClicked: (movie: MovieResponse.Movie) -> Unit,
    onClickTopRatedSeeMore: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Top Rated",
            modifier = Modifier
                .padding(horizontal = 10.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.poppins_semi_bold),
                color = Color.White
            )
        )
        Text(
            text = "See more",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable { onClickTopRatedSeeMore() },
            style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle(R.font.poppins_semi_bold),
                color = ViewAllTextColor
            )
        )
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
                    Modifier.clickable { onMovieClicked(movie) })
            }
        }
    }
}

@Composable
fun TrendingMovies(
    modelStateOfTrendingMovies: MovieState,
    onMovieClicked: (movie: MovieResponse.Movie) -> Unit,
    onSeeMoreClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Trending",
            modifier = Modifier
                .padding(horizontal = 10.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle(R.font.poppins_semi_bold),
                color = Color.White
            )
        )
        Text(
            text = "See more",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable { onSeeMoreClicked() },
            style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle(R.font.poppins_medium),
                color = ViewAllTextColor
            )
        )
    }
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            horizontal = 10.dp, vertical = 10.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (modelStateOfTrendingMovies.isLoading) {
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
            itemsIndexed(modelStateOfTrendingMovies.data) { index, movie ->
                MovieItems(
                    movie = movie,
                    Modifier.clickable { onMovieClicked(movie) })
            }
        }

    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search
    )
    BottomNavigation(
        backgroundColor = BottomAppBarColor,
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