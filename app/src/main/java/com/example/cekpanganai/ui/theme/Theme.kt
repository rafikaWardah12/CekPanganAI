package com.example.cekpanganai.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cekpanganai.ui.utils.Elevation
import com.example.cekpanganai.ui.utils.LocalElevations

private val DarkColorScheme = darkColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = Color.Cyan,
    surface = PrimaryBackground,
    onSurface = TextPrimary,
    background = PrimaryBackground,
    onBackground = TextSecondary,
)

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    secondary = GreenSecondary,
    tertiary = BlueSecondary,
    onTertiary = BluePrimary,
    surface = Color.White,
    onSurface = TextPrimary,
    background = PrimaryBackground,
    onBackground = TextSecondary,
    primaryContainer = ContainerPrimary,
    onPrimaryContainer = TextPrimary,
    tertiaryContainer = ContainerPrimary,
    onTertiaryContainer = TextPrimary,
    error = Color.Red,
    onError = Color.White,
)

@Composable
fun CekPanganAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
    val elevations = if (isSystemInDarkTheme()) {
        Elevation(card = 1.dp, default = 1.dp)
    } else {
        Elevation(card = 1.dp, default = 1.dp)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = Shapes
    )
}