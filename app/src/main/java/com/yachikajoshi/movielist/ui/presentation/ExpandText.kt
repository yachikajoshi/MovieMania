package com.yachikajoshi.movielist.ui.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import com.yachikajoshi.movielist.ui.theme.BodyColor
import com.yachikajoshi.movielist.ui.theme.ReadColor


@Composable
fun ExpandingText(text: String, modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember {
        mutableStateOf(text)
    }

    val textLayoutResult = textLayoutResultState.value

    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect
        when {
            isExpanded -> {
                finalText = "$text Show Less"
            }
            textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(3 - 1)
                val showMoreString = "... Show More"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }
                finalText = "$adjustedText$showMoreString"
                isClickable = true
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Text(
            text = finalText.dropLast(9),
            color = BodyColor,
            style = MaterialTheme.typography.body1,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            onTextLayout = { textLayoutResultState.value = it },
        )
        Text(
            text = if (isExpanded) "Show Less" else "Show More",
            color = ReadColor,
            style = MaterialTheme.typography.body1,
            modifier = modifier
                .clickable(enabled = isClickable) { isExpanded = !isExpanded }
                .align(Alignment.BottomEnd)
                .animateContentSize(),
        )
    }
}