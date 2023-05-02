package com.yachikajoshi.movielist.data.model

data class UpcomingMovies(var results: List<Movie>) {
    data class Movie(
        var id: String = "en",
        var original_language: String = "en",
        var title: String = "Evil Dead Rise",
        var overview: String = "Two sisters find an ancient vinyl that gives birth to bloodthirsty demons that run amok in a Los Angeles apartment building and thrusts them into a primal battle for survival as they face the most nightmarish version of family imaginable.",
        var release_date: String = "2023-04-12",
        var poster_path: String = "/d07xtqwq1uriQ1hda6qeu8Skt5m.jpg",
        var backdrop_path: String = "/u5nY7pY2Y58o7dSM9cy6NclOV8V.jpg",
        var genre_ids: List<Int> = emptyList()
    )
}
