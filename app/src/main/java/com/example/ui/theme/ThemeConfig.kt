package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.data.model.AppThemeType

object ThemeConfig {

    // Alibaba Orange (Default)
    val AlibabaOrange = Color(0xFFFF6600)
    val AlibabaOrangeDark = Color(0xFFE55D00)
    val AlibabaOrangeLight = Color(0xFFFF8533)
    val AlibabaBlack = Color(0xFF1E1E1E)
    val AlibabaYellow = Color(0xFFFFBA00)
    val AlibabaBgLight = Color(0xFFF4F5F7)

    // Emerald Green
    val GreenPrimary = Color(0xFF059669)
    val GreenSecondary = Color(0xFF10B981)

    // Neon Purple
    val PurpleNeonPrimary = Color(0xFF8B5CF6)
    val PurpleNeonSecondary = Color(0xFFA855F7)

    // Ocean Blue
    val OceanBluePrimary = Color(0xFF0284C7)
    val OceanBlueSecondary = Color(0xFF0EA5E9)

    fun getColorScheme(themeType: AppThemeType, isDark: Boolean): ColorScheme {
        return when (themeType) {
            AppThemeType.FIRE_ORANGE -> {
                if (isDark) {
                    darkColorScheme(
                        primary = AlibabaOrangeLight,
                        onPrimary = Color.Black,
                        primaryContainer = Color(0xFF4D1F00),
                        onPrimaryContainer = Color(0xFFFFDBC8),
                        secondary = AlibabaYellow,
                        background = Color(0xFF121212),
                        surface = Color(0xFF1E1E1E),
                        onBackground = Color(0xFFEDEDED),
                        onSurface = Color(0xFFEDEDED)
                    )
                } else {
                    lightColorScheme(
                        primary = AlibabaOrange,
                        onPrimary = Color.White,
                        primaryContainer = Color(0xFFFFE0CC),
                        onPrimaryContainer = Color(0xFF4D1F00),
                        secondary = Color(0xFF1E1E1E),
                        background = AlibabaBgLight,
                        surface = Color.White,
                        onBackground = Color(0xFF1E1E1E),
                        onSurface = Color(0xFF1E1E1E)
                    )
                }
            }

            AppThemeType.LIGHT_GREEN -> {
                if (isDark) {
                    darkColorScheme(
                        primary = GreenSecondary,
                        onPrimary = Color.Black,
                        primaryContainer = Color(0xFF064E3B),
                        secondary = Color(0xFF34D399),
                        background = Color(0xFF0B1914),
                        surface = Color(0xFF122820),
                        onBackground = Color.White,
                        onSurface = Color.White
                    )
                } else {
                    lightColorScheme(
                        primary = GreenPrimary,
                        onPrimary = Color.White,
                        primaryContainer = Color(0xFFD1FAE5),
                        secondary = Color(0xFF065F46),
                        background = Color(0xFFF0FDF4),
                        surface = Color.White,
                        onBackground = Color(0xFF1E1E1E),
                        onSurface = Color(0xFF1E1E1E)
                    )
                }
            }

            AppThemeType.OCEAN_MAN_CHAIR -> {
                if (isDark) {
                    darkColorScheme(
                        primary = Color(0xFF38BDF8),
                        onPrimary = Color(0xFF082F49),
                        primaryContainer = Color(0xFF0C4A6E),
                        secondary = Color(0xFFF59E0B),
                        background = Color(0xFF081420),
                        surface = Color(0xFF0F2338),
                        onBackground = Color.White,
                        onSurface = Color.White
                    )
                } else {
                    lightColorScheme(
                        primary = Color(0xFF0284C7),
                        onPrimary = Color.White,
                        primaryContainer = Color(0xFFE0F2FE),
                        secondary = Color(0xFFD97706),
                        background = Color(0xFFF0F9FF),
                        surface = Color.White,
                        onBackground = Color(0xFF0F172A),
                        onSurface = Color(0xFF0F172A)
                    )
                }
            }

            AppThemeType.NEON_PURPLE -> {
                if (isDark) {
                    darkColorScheme(
                        primary = PurpleNeonPrimary,
                        onPrimary = Color.Black,
                        primaryContainer = Color(0xFF3B0764),
                        secondary = Color(0xFFE879F9),
                        background = Color(0xFF0F071C),
                        surface = Color(0xFF1E1035),
                        onBackground = Color.White,
                        onSurface = Color.White
                    )
                } else {
                    lightColorScheme(
                        primary = Color(0xFF7C3AED),
                        onPrimary = Color.White,
                        primaryContainer = Color(0xFFF3E8FF),
                        secondary = Color(0xFF581C87),
                        background = Color(0xFFFAF5FF),
                        surface = Color.White,
                        onBackground = Color(0xFF1E1E1E),
                        onSurface = Color(0xFF1E1E1E)
                    )
                }
            }

            AppThemeType.OCEAN_BLUE, AppThemeType.CUSTOM_PHOTO -> {
                if (isDark) {
                    darkColorScheme(
                        primary = OceanBlueSecondary,
                        onPrimary = Color.Black,
                        primaryContainer = Color(0xFF075985),
                        secondary = Color(0xFF38BDF8),
                        background = Color(0xFF0B132B),
                        surface = Color(0xFF1C2541),
                        onBackground = Color.White,
                        onSurface = Color.White
                    )
                } else {
                    lightColorScheme(
                        primary = OceanBluePrimary,
                        onPrimary = Color.White,
                        primaryContainer = Color(0xFFE0F2FE),
                        secondary = Color(0xFF0369A1),
                        background = Color(0xFFF8FAFC),
                        surface = Color.White,
                        onBackground = Color(0xFF0F172A),
                        onSurface = Color(0xFF0F172A)
                    )
                }
            }
        }
    }
}
