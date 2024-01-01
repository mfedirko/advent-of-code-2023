package io.mfedirko.aoc.day05

import io.mfedirko.aoc.Solution

/**
 * https://adventofcode.com/2023/day/5
 */
object Day5 : Solution<Long> {

    fun partOne(input: Sequence<String>, seedsAsRange: Boolean = false): Long {
        with(InputParser(seedsAsRange)) {
            input.forEach { parseNext(it) }
            return almanac.lowestLocation
        }
    }

    override fun partOne(input: Sequence<String>): Long {
        return partOne(input, false)
    }

    override fun partTwo(input: Sequence<String>): Long {
        return partOne(input, seedsAsRange = true)
    }

}
