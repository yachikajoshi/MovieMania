package com.yachikajoshi.movielist.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yachikajoshi.movielist.R

val OpenSans = FontFamily(
    Font(R.font.opensans_bold, FontWeight.Bold),
    Font(R.font.opensans_light, FontWeight.Light),
    Font(R.font.opensans_egular, FontWeight.Normal),
    Font(R.font.opensans_medium, FontWeight.Medium),
    Font(R.font.opensans_semibold, FontWeight.SemiBold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    subtitle1 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    body2 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    h6 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    caption = TextStyle(
        fontFamily = OpenSans,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)
