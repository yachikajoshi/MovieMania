package com.yachikajoshi.movielist.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.yachikajoshi.movielist.data.datasource.MoviePagingSource
import com.yachikajoshi.movielist.data.datasource.SearchPagingSource
import com.yachikajoshi.movielist.data.datasource.TopRatedMovieSource
import com.yachikajoshi.movielist.domain.use_case.ListAllMoviesUseCase
import com.yachikajoshi.movielist.domain.use_case.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class AllMoviesViewModel @Inject constructor(
    val useCase: ListAllMoviesUseCase,
    private val searchUseCase: SearchMovieUseCase
) : ViewModel() {

    val moviePager = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(useCase)
    }.flow.cachedIn(viewModelScope)

    val topRatedMoviePager = Pager(PagingConfig(pageSize = 20)) {
        TopRatedMovieSource(useCase)
    }.flow.cachedIn(viewModelScope)

    private val _search = MutableStateFlow("")

    val search = _search.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchMovie = search.debounce(300.milliseconds).flatMapLatest { query ->
        Pager(
            PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            )
        ) {
            SearchPagingSource(
                search = query,
                searchMovieUseCase = searchUseCase
            )
        }.flow.cachedIn(viewModelScope)
    }

    fun setSearch(query: String) {
        _search.value = query
    }
}