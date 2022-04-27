package com.tck.kotlintrain.consolecolor

import java.util.*

/**
 *
 * @author tck88
 *
 * @date 2022-04-27
 */
enum class AnsiColor(private val colorNumber: Byte) {
    BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), WHITE(7);

    companion object {
        private const val prefix = "\u001B"
        const val RESET = "$prefix[0m"
        private val isCompatible = true
    }

    val regular get() = if (isCompatible) "$prefix[0;3${colorNumber}m" else ""
    val bold get() = if (isCompatible) "$prefix[1;3${colorNumber}m" else ""
    val underline get() = if (isCompatible) "$prefix[4;3${colorNumber}m" else ""
    val background get() = if (isCompatible) "$prefix[4${colorNumber}m" else ""
    val highIntensity get() = if (isCompatible) "$prefix[0;9${colorNumber}m" else ""
    val boldHighIntensity get() = if (isCompatible) "$prefix[1;9${colorNumber}m" else ""
    val backgroundHighIntensity get() = if (isCompatible) "$prefix[0;10${colorNumber}m" else ""
}

fun getColor(type: Int): AnsiColor {
    return when (type % 7) {
        1 -> AnsiColor.RED
        2 -> AnsiColor.GREEN
        3 -> AnsiColor.YELLOW
        4 -> AnsiColor.BLUE
        5 -> AnsiColor.MAGENTA
        6 -> AnsiColor.CYAN
        else -> {
            AnsiColor.WHITE
        }
    }
}

