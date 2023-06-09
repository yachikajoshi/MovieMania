package com.yachikajoshi.movielist.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.yachikajoshi.movielist.common.Constants.GENRES

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000)
        )
    )
    background(
        Brush.linearGradient(
            colors = listOf(Color.LightGray, Color.Gray, Color.LightGray),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned { size = it.size }
}

fun List<Int>.getGenreNames(): String {
    var genresString = mutableListOf<String>()
    this.forEach {
        if (GENRES[it].toString() != "null") {
            genresString.add(GENRES[it].toString())
        }
    }
    return genresString.toString().removePrefix("[").removeSuffix("]")
}

fun Int.getDuration():String{
    val hours = this / 60
    val minutes = this % 60
    return "${hours}h ${minutes}m"
}

fun String.getLanguageName(): String {
    val lang = when (this) {
        "en" -> "English"
        "ja" -> "Japanese"
        "hi" -> "Hindi"
        "fr" -> "French"
        "in" -> "Indonesian"
        "it" -> "Italian"
        "ko" -> "Korean"
        "la" -> "Latin"
        "pa" -> "Punjabi"
        "pt" -> "Portuguese"
        "ru" -> "Russian"
        "sv" -> "swedish"
        "ta" -> "Tamil"
        "te" -> "Telugu"
        "ml" -> "Malayalam"
        "vi" -> "Vietnamese"
        "zh" -> "Chinese"
        else -> {
            this
        }
    }
    return lang
}
