package com.yachikajoshi.movielist.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.domain.use_case.ListAllMoviesUseCase

class MoviePagingSource(val allMoviesUseCase: ListAllMoviesUseCase) :
    PagingSource<Int, MovieResponse.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse.Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val responseTrending = allMoviesUseCase.getAllTrending(nextPageNumber)
            val nextKey = if (responseTrending.page > responseTrending.total_pages) {
                null
            } else {
                nextPageNumber + 1
            }
            Log.d("sdfafd", "load: hello}")
            LoadResult.Page(
                data = responseTrending.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.d("sdfafd", "load: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse.Movie>): Int? =
        state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.prevKey?.plus(1)
        }
}