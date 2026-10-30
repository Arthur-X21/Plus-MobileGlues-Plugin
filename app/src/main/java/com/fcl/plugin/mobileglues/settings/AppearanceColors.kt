package com.fcl.plugin.mobileglues.settings

object AppearanceColors {
    val palette = listOf(
        0xFF0001F9.toInt(),
        0xFFF44336.toInt(),
        0xFFE91E63.toInt(),
        0xFF9C27B0.toInt(),
        0xFF673AB7.toInt(),
        0xFF3F51B5.toInt(),
        0xFF2196F3.toInt(),
        0xFF03A9F4.toInt(),
        0xFF00BCD4.toInt(),
        0xFF009688.toInt(),
        0xFF4CAF50.toInt(),
        0xFF8BC34A.toInt(),
        0xFFCDDC39.toInt(),
        0xFFFFC107.toInt(),
        0xFFFF9800.toInt(),
        0xFFFF5722.toInt(),
        0xFF795548.toInt(),
        0xFF607D8B.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
    )

    fun parseHex(input: String): Int? {
        val hex = input.trim().removePrefix("#")
        if (hex.length != 6 && hex.length != 8) return null
        if (!hex.all { it.digitToIntOrNull(16) != null }) return null

        val value = hex.toLongOrNull(16) ?: return null
        return if (hex.length == 6) {
            (0xFF000000L or value).toInt()
        } else {
            value.toInt()
        }
    }

    fun toHex(color: Int): String {
        val alpha = color ushr 24
        return if (alpha == 0xFF) {
            "#%06X".format(color and 0xFFFFFF)
        } else {
            "#%08X".format(color.toLong() and 0xFFFFFFFFL)
        }
    }
}
