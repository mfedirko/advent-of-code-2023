package io.mfedirko.aoc.day09

import io.mfedirko.aoc.Solution

object Day9 : Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return extrapolateAndSum(input) { diff, seq ->
            seq.last() + diff
        }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return extrapolateAndSum(input) { diff, seq ->
            seq.first() - diff
        }
    }

    private fun extrapolateAndSum(input: Sequence<String>, diffMapper: (Int, List<Int>) -> Int): Int {
        return input
            .map { parseIntList(it) }
            .map { extrapolate(it, diffMapper) }
            .sum()
    }

    private fun parseIntList(line: String): List<Int> {
        return line.split(" ").map { it.toInt() }
    }

    private fun extrapolate(list: List<Int>, diffMapper: (Int, List<Int>) -> Int): Int {
        if (list.all { it == list[0] }) return list[0]

        val diffs: List<Int> = (1 until list.size).map { list[it] - list[it - 1] }
        val nextDiff = extrapolate(diffs, diffMapper)
        return diffMapper(nextDiff, list)
    }
}