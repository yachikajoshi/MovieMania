package com.yachikajoshi.movielist.data.model

import com.yachikajoshi.movielist.domain.model.ComingSoon
import com.yachikajoshi.movielist.domain.model.Movie
import com.yachikajoshi.movielist.domain.model.MovieDetail

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
