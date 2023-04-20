package com.yachikajoshi.movielist.data.model

import com.yachikajoshi.movielist.domain.model.ComingSoon
import com.yachikajoshi.movielist.domain.model.Movie
import com.yachikajoshi.movielist.domain.model.MovieDetail

data class Movies(var items: List<MovieDetail>, var errorMessage: String) {
    data class MovieDetail(
        var id: String = "",
        var title: String = "Default",
        var fullTitle: String = "Bla Bla",
        var year: String = "1997",
        var crew: String = "Dom",
        var image: String="https://picsum.photos/id/237/200/300",
        var rankUpDown: String="",
        var imDbRating: String="",
        var releaseState: String="",
        var stars: String="",
        var genres: String=""
    )
}

fun Movies.MovieDetail.toDomainMovie(): Movie =
    Movie(
        movieId = this.id,
        title = this.title,
        image = this.image
    )

fun Movies.MovieDetail.toDomainComingSoon(): ComingSoon =
    ComingSoon(
        movieId = this.id,
        title = this.title,
        image = this.image,
        genres = this.genres,
        releaseState = this.releaseState
    )

fun Movies.MovieDetail.toDomainMovieDetail(): MovieDetail =
    MovieDetail(
        image = this.image,
        fullTitle = this.fullTitle,
        crew = this.crew,
        imDbRating = this.imDbRating,
        stars = this.stars
    )
