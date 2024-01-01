package io.mfedirko.aoc.day01


/**
 * https://adventofcode.com/2023/day/1
 */
object Day1 {
    private val strToDigit = mapOf(
        Pair("one", 1), Pair("1", 1), Pair("two", 2), Pair("2", 2), Pair("three", 3), Pair("3", 3),
        Pair("four", 4), Pair("4", 4), Pair("five", 5), Pair("5", 5), Pair("six", 6), Pair("6", 6),
        Pair("seven", 7), Pair("7", 7), Pair("eight", 8), Pair("8", 8), Pair("nine", 9), Pair("9", 9)
    )

    private val allDigits = strToDigit.keys

    fun partOne(input: Sequence<String>): Int {
        return input
            .map { it.first { ch -> ch.isDigit() }.digitToInt() * 10 + it.last { ch -> ch.isDigit() }.digitToInt() }
            .sum()
    }

    fun partTwo(input: Sequence<String>): Int {
        return input
            .map { toInt(it.findAnyOf(allDigits)!!) * 10 + toInt(it.findLastAnyOf(allDigits)!!) }
            .sum()
    }

    private fun toInt(pair: Pair<Int, String>): Int {
        return strToDigit[pair.second]!!
    }


}
