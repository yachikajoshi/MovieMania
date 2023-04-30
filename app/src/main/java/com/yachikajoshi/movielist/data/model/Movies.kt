package com.yachikajoshi.movielist.data.model

data class Movies(var items: List<MovieDetail>, var errorMessage: String) {
    data class MovieDetail(
        var id: String = "",
        var title: String = "Default",
        var fullTitle: String = "Bla Bla",
        var year: String = "1997",
        var crew: String = "Dom",
        var image: String = "https://picsum.photos/id/237/200/300",
        var rankUpDown: String = "",
        var imDbRating: String = "",
        var releaseState: String = "",
        var stars: String = "",
        var genres: String = ""
    )
}
