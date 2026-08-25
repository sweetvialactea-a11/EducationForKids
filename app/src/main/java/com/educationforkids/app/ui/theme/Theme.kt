package com.educationforkids.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EducationColors = lightColorScheme(
    primary = Color(0xFF52C63B),
    onPrimary = Color.White,
    secondary = Color(0xFF31A4EE),
    tertiary = Color(0xFF8844DC),
    background = Color(0xFFFCFDFC),
    surface = Color.White,
    onBackground = Color(0xFF14213A),
    onSurface = Color(0xFF14213A)
)

@Composable
fun EducationForKidsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EducationColors,
        typography = Typography(),
        content = content
    )
}
