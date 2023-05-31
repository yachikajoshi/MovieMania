package com.yachikajoshi.movielist.data.model

data class MovieResponse(
    val page: Int,
    var results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Movie(
        var id: String = "",
        var original_language: String = "",
        var title: String = "",
        var overview: String = "",
        var release_date: String = "",
        var poster_path: String? = null,
        var backdrop_path: String = "",
        var genre_ids: List<Int> = emptyList(),
        val first_air_date: String = "",
        val media_type: String = "",
        val name: String = "",
        val original_name: String = "",
        val popularity: Double = 0.0,
        val vote_average: Double = 0.0
    )
}
