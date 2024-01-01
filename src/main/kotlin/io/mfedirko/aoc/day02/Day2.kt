package io.mfedirko.aoc.day02

import io.mfedirko.aoc.InputReaderUtil

/**
 * https://adventofcode.com/2023/day/2
 */
object Day2 {
    fun partOne(actual: Map<String, Int>): Int {
        return InputReaderUtil.read("day2")
            .map { parseGame(it) }
            .filter { it.isPossible(actual) }
            .map { it.id }
            .sum()
    }

    fun partTwo(): Int {
        return InputReaderUtil.read("day2")
            .map { parseGame(it) }
            .map { it.toPower() }
            .sum()
    }

    private fun parseGame(line: String): Game {
        val id: Int = line.substring("Game ".length, line.indexOf(':')).toInt()
        val counts = line.substringAfter(": ").split(", ", "; ").groupBy(
            { segment -> segment.split(' ')[1] },
            { segment -> segment.split(' ')[0].toInt()}
        )
        return Game(id, counts)
    }

    class Game(val id: Int, counts: Map<String, List<Int>>) {
        private val max: Map<String, Int>
        init {
            val maxPairs: Array<Pair<String, Int>> = counts.entries.map { e -> e.key to e.value.max() }.toTypedArray()
            max = mapOf(*maxPairs)
        }

        fun isPossible(actual: Map<String, Int>): Boolean {
            return max.all { e -> (actual[e.key] ?: 0) >= e.value }
        }

        fun toPower(): Int {
            return listOf("red", "green", "blue").map { max[it] ?: 0 }.fold(1) { acc, i -> acc * i }
        }
    }
}

fun main() {
    println(Day2.partTwo())
}
