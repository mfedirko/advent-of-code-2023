package io.mfedirko.aoc.day01

import io.mfedirko.aoc.Solution


/**
 * https://adventofcode.com/2023/day/1
 */
object Day1 : Solution<Int> {
    private val strToDigit = mapOf(
        "one" to 1, "1" to 1, "two" to 2, "2" to 2, "three" to 3, "3" to 3,
        "four" to 4, "4" to 4, "five" to 5, "5" to 5, "six" to 6, "6" to 6,
        "seven" to 7, "7" to 7, "eight" to 8, "8" to 8, "nine" to 9, "9" to 9
    )

    private val allDigits = strToDigit.keys

    override fun partOne(input: Sequence<String>): Int {
        return input
            .map { it.first { ch -> ch.isDigit() }.digitToInt() * 10 + it.last { ch -> ch.isDigit() }.digitToInt() }
            .sum()
    }

    override fun partTwo(input: Sequence<String>): Int {
        return input
            .map { toInt(it.findAnyOf(allDigits)!!) * 10 + toInt(it.findLastAnyOf(allDigits)!!) }
            .sum()
    }

    private fun toInt(pair: Pair<Int, String>): Int {
        return strToDigit[pair.second]!!
    }
}
