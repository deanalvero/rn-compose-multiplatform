package io.github.deanalvero.rn.cmp.parser

import androidx.compose.ui.graphics.Color

object ColorParser {
    private val namedColors = mapOf(
        "red" to Color.Red, "blue" to Color.Blue, "green" to Color.Green,
        "white" to Color.White, "black" to Color.Black, "gray" to Color.Gray,
        "yellow" to Color.Yellow, "cyan" to Color.Cyan, "magenta" to Color.Magenta,
        "transparent" to Color.Transparent, "darkgray" to Color.DarkGray,
        "lightgray" to Color.LightGray
    )

    fun parse(colorString: String): Color? {
        return try {
            when {
                colorString.startsWith("#") -> parseHex(colorString)
                colorString.startsWith("rgb(") -> parseRgb(colorString)
                colorString.startsWith("rgba(") -> parseRgba(colorString)
                else -> namedColors[colorString.lowercase()]
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun parseHex(hex: String): Color {
        var hexValue = hex.substring(1)
        if (hexValue.length == 3) {
            hexValue = hexValue.map { "$it$it" }.joinToString("")
        }

        val colorLong = if (hexValue.length == 6) {
            "FF$hexValue".toLong(16)
        } else {
            hexValue.toLong(16)
        }

        val alpha = ((colorLong shr 24) and 0xFF).toInt()
        val red = ((colorLong shr 16) and 0xFF).toInt()
        val green = ((colorLong shr 8) and 0xFF).toInt()
        val blue = (colorLong and 0xFF).toInt()

        return Color(red, green, blue, alpha)
    }

    private fun parseRgb(rgb: String): Color {
        val values = rgb.removePrefix("rgb(").removeSuffix(")")
            .split(",").map { it.trim().toInt() }
        return Color(values[0], values[1], values[2])
    }

    private fun parseRgba(rgba: String): Color {
        val values = rgba.removePrefix("rgba(").removeSuffix(")")
            .split(",").map { it.trim() }
        return Color(
            values[0].toInt(),
            values[1].toInt(),
            values[2].toInt(),
            (values[3].toFloat() * 255).toInt()
        )
    }
}
