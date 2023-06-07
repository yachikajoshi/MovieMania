package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.ui.theme.Background
import com.yachikajoshi.movielist.ui.theme.BottomAppBarColor
import com.yachikajoshi.movielist.ui.theme.TextColor

@Composable
fun Search(
    onBackPressed: () -> Unit,
    searchViewModel: AllMoviesViewModel,
    onMovieClicked: (MovieResponse.Movie) -> Unit
) {
    val search by searchViewModel.search.collectAsStateWithLifecycle()

    val products: LazyPagingItems<MovieResponse.Movie> =
        searchViewModel.searchMovie.collectAsLazyPagingItems()

    val focusController = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        TextField(
            value = search,
            onValueChange = { value ->
                searchViewModel.setSearch(value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            textStyle = TextStyle(color = TextColor, fontSize = 18.sp),
            placeholder = {
                Text(
                    text = "Search for movies",
                    color = TextColor
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                        .clickable {
                            searchViewModel.setSearch("")
                            focusController.clearFocus()
                            onBackPressed()
                        }
                )
            },
            trailingIcon = {
                if (search.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            searchViewModel.setSearch("")// Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(10), // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                leadingIconColor = Color.White,
                trailingIconColor = Color.White,
                backgroundColor = BottomAppBarColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            contentPadding = PaddingValues(
                horizontal = 10.dp, vertical = 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(products.itemCount) { index ->
                MovieItems(
                    movie = products[index]!!,
                    modifier = Modifier.clickable {
                        focusController.clearFocus()
                        onMovieClicked(products[index]!!)
                    })
            }
        }
    }
}