package com.endrazhafir.siamobile.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.endrazhafir.siamobile.R

// 1. PENDAFTARAN FONT
val urbanistFontFamily = FontFamily(
    Font(R.font.urbanistthin, FontWeight.Thin),
    Font(R.font.urbanistthinitalic, FontWeight.Thin, FontStyle.Italic),
    Font(R.font.urbanistextralight, FontWeight.ExtraLight),
    Font(R.font.urbanistextralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.urbanistlight, FontWeight.Light),
    Font(R.font.urbanistlightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.urbanistregular, FontWeight.Normal),
    Font(R.font.urbanistitalic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.urbanistmedium, FontWeight.Medium),
    Font(R.font.urbanistmediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.urbanistsemibold, FontWeight.SemiBold),
    Font(R.font.urbanistsemibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.urbanistbold, FontWeight.Bold),
    Font(R.font.urbanistbolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.urbanistextrabold, FontWeight.ExtraBold),
    Font(R.font.urbanistextrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.urbanistblack, FontWeight.Black),
    Font(R.font.urbanistblackitalic, FontWeight.Black, FontStyle.Italic)
)

// 2. STYLE DASAR
val baseTextStyle = TextStyle(
    fontFamily = urbanistFontFamily
)

// 3. KAMUS TYPOGRAPHY
val Typography = Typography(
    // Dipakai di: Judul "Statistik Mata Kuliah" [StatsActivity] & "Login" [LoginScreen]
    displayLarge = baseTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    // Dipakai di: Judul "Sistem Manajemen" [DashboardScreen]
    headlineLarge = baseTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    // Dipakai di: Label "Email" & "Password" [LoginScreen]
    titleLarge = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    // Dipakai di: Judul StatCard "Total Dosen" [DashboardScreen]
    titleMedium = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    // Dipakai di: Header & Baris Tabel [StatsActivity]
    bodyLarge = baseTextStyle.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    // Dipakai di: Teks tombol "Masuk" [LoginScreen]
    bodyMedium = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    // Dipakai di: Teks tombol "Tambah" [StatsActivity] & Welcome Text [LoginScreen]
    bodySmall = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),
    // Dipakai di: Badge "SKS" [StatsActivity]
    labelSmall = baseTextStyle.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp
    )
)