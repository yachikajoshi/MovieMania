package com.yachikajoshi.movielist.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetail(
    var fullTitle: String = "",
    var crew: String = "",
    var image: String = "",
    var imDbRating: String = "",
    var stars: String = ""
) : Parcelable
