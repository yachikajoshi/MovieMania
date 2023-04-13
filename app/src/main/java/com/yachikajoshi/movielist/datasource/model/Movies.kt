package com.yachikajoshi.movielist.datasource.model

data class Movies(var items: MovieDetail) {
    data class MovieDetail(
        var id: String,
        var title: String,
        var fullTitle: String,
        var year: String,
        var crew: String,
        var image: String,
        var rankUpDown: String,
        var imDbRating: String,
        var releaseState: String,
        var stars: String,
        var genres: String
    )
}
