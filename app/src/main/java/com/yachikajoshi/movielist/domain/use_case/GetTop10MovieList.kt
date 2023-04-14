package com.yachikajoshi.movielist.domain.use_case

import com.yachikajoshi.movielist.domain.repo.Top10MovieRepository
import javax.inject.Inject

class GetTop10MovieList @Inject constructor(private val top10MovieRepository: Top10MovieRepository) {
}