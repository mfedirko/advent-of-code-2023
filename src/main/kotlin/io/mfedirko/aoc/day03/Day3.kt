package io.mfedirko.aoc.day03

import io.mfedirko.aoc.InputReaderUtil

/**
 * https://adventofcode.com/2023/day/3
 */
object Day3 {
    private val nonSpecialChars = setOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')

    fun partOne(input: Sequence<String>): Int {
        return input
            .toList()
            .let { Schematic(it).partNumbers }
            .sum()
    }

    fun partTwo(input: Sequence<String>): Int {
        return input
            .toList()
            .let { Schematic(it).gearRatios }
            .sum()
    }

    class Schematic(private val grid: List<String>) {
        private val numberRegex = "\\d+".toRegex()
        private val gearRegex = Regex.fromLiteral("*")
        val partNumbers: List<Int>
        val gearRatios: List<Int>

        init {
            partNumbers = findAll(numberRegex)
                .filter { isBoundedBySpecialChars(it) }
                .map { it.second.value.toInt() }

            gearRatios = findAll(gearRegex)
                .map { getBoundingNumbers(it) }
                .filter { it.size == 2 }
                .map { toGearRatio(it) }
        }

        private fun findAll(regex: Regex): List<Pair<Int, MatchResult>> = grid.flatMapIndexed { index, s -> regex.findAll(s).map { index to it } }

        private fun getBoundingNumbers(indexMatch: Pair<Int, MatchResult>): List<Int> {
            val (rowRange, colRange) = getBoundingRectangle(indexMatch)
            return rowRange.flatMap { numberRegex.findAll(grid[it]) }
                .filter { mr -> mr.range.isOverlappingWith(colRange) }
                .map { mr -> mr.value.toInt() }
        }

        private fun toGearRatio(it: List<Int>) = it[0] * it[1]

        private fun isBoundedBySpecialChars(indexMatch: Pair<Int, MatchResult>): Boolean {
            val (rowRange, colRange) = getBoundingRectangle(indexMatch)
            return rowRange.any { row -> colRange.any { col -> grid[row][col] !in nonSpecialChars } }
        }

        private fun getBoundingRectangle(indexMatch: Pair<Int, MatchResult>): Pair<IntRange, IntRange> {
            val rowRange = ((indexMatch.first - 1)..(indexMatch.first + 1)).filter { row -> row in grid.indices }
            val colRange = ((indexMatch.second.range.first - 1)..(indexMatch.second.range.last + 1)).filter { col -> col in grid[0].indices }
            return rowRange.asIntRange() to colRange.asIntRange()
        }
    }
}

fun IntRange.isOverlappingWith(range: IntRange): Boolean {
    return range.first() <= this.last && range.last() >= this.first
}

fun List<Int>.asIntRange(): IntRange {
    return this.first()..this.last()
}