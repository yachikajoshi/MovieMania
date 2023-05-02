package com.yachikajoshi.movielist.common

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "31c4bdc839d97326ebb5324fa87b0105"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    const val Access_Token =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzMWM0YmRjODM5ZDk3MzI2ZWJiNTMyNGZhODdiMDEwNSIsInN1YiI6IjY0MzdhZTcxODFhN2ZjMDBiZTQ1N2IyNyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rfv-YtiNUkT0sLb5x9tHXVS6Vr784SXsJWtIdkvugiY"

    val GENRES = hashMapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )
}