package com.endrazhafir.siamobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.endrazhafir.siamobile.R
val urbanistFontFamily = FontFamily(
    // Thin (Weight 100)
    Font(R.font.urbanistthin, FontWeight.Thin),
    Font(R.font.urbanistthinitalic, FontWeight.Thin, FontStyle.Italic),

    // ExtraLight (Weight 200)
    Font(R.font.urbanistextralight, FontWeight.ExtraLight),
    Font(R.font.urbanistextralightitalic, FontWeight.ExtraLight, FontStyle.Italic),

    // Light (Weight 300)
    Font(R.font.urbanistlight, FontWeight.Light),
    Font(R.font.urbanistlightitalic, FontWeight.Light, FontStyle.Italic),

    // Regular (Weight 400 - Normal)
    Font(R.font.urbanistregular, FontWeight.Normal),
    Font(R.font.urbanistitalic, FontWeight.Normal, FontStyle.Italic),

    // Medium (Weight 500)
    Font(R.font.urbanistmedium, FontWeight.Medium),
    Font(R.font.urbanistmediumitalic, FontWeight.Medium, FontStyle.Italic),

    // SemiBold (Weight 600)
    Font(R.font.urbanistsemibold, FontWeight.SemiBold),
    Font(R.font.urbanistsemibolditalic, FontWeight.SemiBold, FontStyle.Italic),

    // Bold (Weight 700)
    Font(R.font.urbanistbold, FontWeight.Bold),
    Font(R.font.urbanistbolditalic, FontWeight.Bold, FontStyle.Italic),

    // ExtraBold (Weight 800)
    Font(R.font.urbanistextrabold, FontWeight.ExtraBold),
    Font(R.font.urbanistextrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),

    // Black (Weight 900)
    Font(R.font.urbanistblack, FontWeight.Black),
    Font(R.font.urbanistblackitalic, FontWeight.Black, FontStyle.Italic)
)


val baseTextStyle = TextStyle(
    fontFamily = urbanistFontFamily
)
val Typography = Typography(

    displayLarge = baseTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    titleLarge = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),

    bodyLarge = baseTextStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),

    bodyMedium = baseTextStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    bodySmall = baseTextStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),

    labelSmall = baseTextStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        fontStyle = FontStyle.Italic
    )
)