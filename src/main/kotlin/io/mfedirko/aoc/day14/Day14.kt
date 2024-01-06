package io.mfedirko.aoc.day14

import io.mfedirko.aoc.GridUtil
import io.mfedirko.aoc.Solution

private const val OPEN = '.'
private const val CUBE_ROCK = '#'
private const val ROUND_ROCK = 'O'

object Day14: Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return input.toList()
            .let { Platform(it).totalLoad() }
    }

    override fun partTwo(input: Sequence<String>): Int {
        TODO("Not yet implemented")
    }

    class Platform(initial: List<String>) {
        private val columns by fallIntoPlace(initial)
        val rows: List<String>
            get() = GridUtil.columns(columns)

        fun totalLoad(): Int {
            val grid = rows
            return grid.mapIndexed { i, row ->
                (grid.size - i) * row.count { it == ROUND_ROCK }
            }.sum()
        }

        private fun fallIntoPlace(initial: List<String>) = lazy {
            val finalState = GridUtil.columns(initial).map { fallIntoPlace(it) }.toList()
            finalState
        }

        private fun fallIntoPlace(col: String): String {
            if (col.isEmpty()) return col
            val i = col.indices.firstOrNull {
                col[it] == ROUND_ROCK || col[it] == CUBE_ROCK
            } ?: return col

            return if (col[i] == CUBE_ROCK) {
                col.substring(0, i + 1) + fallIntoPlace(col.substring(i + 1))
            } else if (i > 0) {
                ROUND_ROCK + fallIntoPlace(col.substring(1, i) + OPEN + col.substring(i + 1))
            } else ROUND_ROCK + fallIntoPlace(col.substring(1))
        }
    }
}