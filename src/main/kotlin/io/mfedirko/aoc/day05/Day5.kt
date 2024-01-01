package io.mfedirko.aoc.day05

/**
 * https://adventofcode.com/2023/day/5
 */
object Day5 {

    fun partOne(input: Sequence<String>, seedsAsRange: Boolean = false): Long {
        with(InputParser(seedsAsRange)) {
            input.forEach { parseNext(it) }
            return almanac.lowestLocation
        }
    }

    fun partTwo(input: Sequence<String>): Long {
        return partOne(input, seedsAsRange = true)
    }

}
