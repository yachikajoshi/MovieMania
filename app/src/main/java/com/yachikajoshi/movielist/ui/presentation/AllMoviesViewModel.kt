package com.yachikajoshi.movielist.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.yachikajoshi.movielist.data.datasource.MoviePagingSource
import com.yachikajoshi.movielist.domain.use_case.ListAllMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(val useCase: ListAllMoviesUseCase) : ViewModel() {

    val moviePager = Pager(PagingConfig(pageSize = 20)) {
        MoviePagingSource(useCase)
    }.flow.cachedIn(viewModelScope)
}