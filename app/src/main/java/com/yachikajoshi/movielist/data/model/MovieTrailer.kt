package com.yachikajoshi.movielist.data.model

data class MovieTrailer(
    val id: String = "",
    val results: List<Trailer> = emptyList()
) {
    data class Trailer(val name: String, val key: String)
}
