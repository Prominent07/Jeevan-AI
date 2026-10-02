package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = TealPrimaryContainer,
    onPrimary = TealOnPrimaryContainer,
    primaryContainer = TealPrimary,
    onPrimaryContainer = TealOnPrimary,
    secondary = HealthGreenContainer,
    onSecondary = HealthGreenOnContainer,
    tertiary = SaffronAccent,
    error = UrgentRedContainer,
    onError = UrgentRedOnContainer,
    background = Color(0xFF191C1B),
    surface = Color(0xFF202524),
    onBackground = Color(0xFFE1E3E0),
    onSurface = Color(0xFFE1E3E0)
)

private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    onPrimary = TealOnPrimary,
    primaryContainer = TealPrimaryContainer,
    onPrimaryContainer = TealOnPrimaryContainer,
    secondary = HealthGreen,
    onSecondary = Color.White,
    secondaryContainer = HealthGreenContainer,
    onSecondaryContainer = HealthGreenOnContainer,
    tertiary = SaffronAccent,
    error = UrgentRed,
    onError = Color.White,
    errorContainer = UrgentRedContainer,
    onErrorContainer = UrgentRedOnContainer,
    background = NeutralBackground,
    surface = NeutralSurface,
    surfaceVariant = NeutralSurfaceVariant,
    onBackground = NeutralText,
    onSurface = NeutralText,
    onSurfaceVariant = NeutralTextSecondary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set default false to preserve high-contrast rural health branding
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
