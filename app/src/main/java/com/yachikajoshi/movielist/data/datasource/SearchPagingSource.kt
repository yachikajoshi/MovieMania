package com.yachikajoshi.movielist.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yachikajoshi.movielist.data.model.MovieResponse
import com.yachikajoshi.movielist.domain.use_case.SearchMovieUseCase

class SearchPagingSource(
    val search: String,
    val searchMovieUseCase: SearchMovieUseCase
) :
    PagingSource<Int, MovieResponse.Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse.Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = searchMovieUseCase.searchMovie(search, nextPageNumber)
            val nextKey = if (response.page > response.total_pages) {
                null
            } else {
                nextPageNumber + 1
            }
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse.Movie>): Int? =
        state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.prevKey?.plus(1)
        }
}