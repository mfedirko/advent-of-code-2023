package io.mfedirko.aoc.day09

import io.mfedirko.aoc.Solution

object Day9 : Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return input
            .map { parseIntList(it) }
            .map { extrapolateNextInt(it) }
            .sum()
    }

    override fun partTwo(input: Sequence<String>): Int {
        return input
            .map { parseIntList(it) }
            .map { extrapolatePrevInt(it) }
            .sum()
    }

    private fun parseIntList(line: String): List<Int> {
        return line.split(" ").map { it.toInt() }
    }

    private fun extrapolateNextInt(list: List<Int>): Int {
        return extrapolate(list) {
                diff: Int, ints: List<Int> -> ints.last() + diff
        }
    }

    private fun extrapolatePrevInt(list: List<Int>): Int {
        return extrapolate(list) {
                diff: Int, ints: List<Int> -> ints.first() - diff
        }
    }

    private fun extrapolate(list: List<Int>, diffMapper: (Int, List<Int>) -> Int): Int {
        if (list.all { it == list[0] }) return list[0]

        val diffs: List<Int> = (1 until list.size).map { list[it] - list[it - 1] }
        val nextDiff = extrapolate(diffs, diffMapper)
        return diffMapper(nextDiff, list)
    }
}