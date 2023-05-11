package com.yachikajoshi.movielist.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.domain.use_case.ListAllMoviesUseCase

class TopRatedMovieSource(val allMoviesUseCase: ListAllMoviesUseCase) :
    PagingSource<Int, MovieResponse.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse.Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val responseTrending = allMoviesUseCase.getAllTopRatedMovies(nextPageNumber)
            val nextKey = if (responseTrending.page > responseTrending.total_pages) {
                null
            } else {
                nextPageNumber + 1
            }
            LoadResult.Page(
                data = responseTrending.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse.Movie>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
}