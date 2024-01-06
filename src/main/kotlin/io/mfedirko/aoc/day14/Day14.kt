package io.mfedirko.aoc.day14

import io.mfedirko.aoc.GridUtil.rowsToColumns
import io.mfedirko.aoc.GridUtil.columnsToRows
import io.mfedirko.aoc.Solution

private const val GROUND = '.'
private const val CUBE_ROCK = '#'
private const val ROUND_ROCK = 'O'

object Day14: Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return input.toList()
            .let { Platform(it, part2 = false).totalLoad() }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return input.toList()
            .let { Platform(it, part2 = true).totalLoad() }
    }

    class Platform(private val initial: List<String>, private val part2: Boolean) {
        fun totalLoad(): Int {
            val cols = rowsToColumns(initial)
            return if (part2) {
                val sampleSize = 180 // smallest sample size with cycles
                val (_, sequence) = (0 until sampleSize).fold((cols to mutableListOf<Int>())) { acc, _ ->
                    val (grid, seq) = acc
                    val nextGrid = spinCycle(grid)
                    val totalLoad = totalLoad(columnsToRows(nextGrid))
                    seq.add(totalLoad)
                    nextGrid to seq
                }
                deriveSequenceValue(sequence,  targetIndex = 1_000_000_000)
            } else {
                val rows = columnsToRows(fallIntoPlaceAll(cols))
                totalLoad(rows)
            }
        }

        private fun deriveSequenceValue(sequence: List<Int>, targetIndex: Int): Int {
            val len = 3 // any value less than cycle length
            var i = 0
            var firstCycleIndex = -1
            while (firstCycleIndex < 0 && i < sequence.size - len) {
                i += len
                firstCycleIndex = sequence.indices.firstOrNull { index ->
                    index > i + len
                    && (index  until index + len).all { k -> sequence[k] == sequence[i + k - index]  }
                } ?: -1
            }

            if (firstCycleIndex == -1) error("No cycles found in sequence of length ${sequence.size}. Try with a larger sample of the sequence")

            val cycleLen = firstCycleIndex - i
            val position = (targetIndex - i - 1) % cycleLen
            return sequence[i + position]
        }

        private fun spinCycle(initial: List<String>): List<String> {
            return initial // north projection initially
                .let { fallIntoPlaceAll(it) }
                .let { northProjection(it) } // west
                .let { fallIntoPlaceAll(it) }
                .let { southProjection(it) } // south
                .let { fallIntoPlaceAll(it) }
                .let { southProjection(eastProjection(it)) } // east
                .let { fallIntoPlaceAll(it) }
                .let { northProjection(southProjection(northProjection(it))) }  // return as north projection (columns)

        }

        private fun totalLoad(grid: List<String>): Int {
            return grid.mapIndexed { i, row ->
                (grid.size - i) * row.count { it == ROUND_ROCK }
            }.sum()
        }

        private fun fallIntoPlaceAll(initial: List<String>): List<String> {
            return initial.map { fallIntoPlace(it) }.toList()
        }

        private fun fallIntoPlace(col: String): String {
            val i = col.indices.firstOrNull {
                col[it] == ROUND_ROCK || col[it] == CUBE_ROCK
            } ?: return col

            return if (col[i] == CUBE_ROCK) {
                col.substring(0, i + 1) + fallIntoPlace(col.substring(i + 1))
            } else if (i > 0) {
                ROUND_ROCK + fallIntoPlace(col.substring(1, i) + GROUND + col.substring(i + 1))
            } else ROUND_ROCK + fallIntoPlace(col.substring(1))
        }

        private fun northProjection(initial: List<String>): List<String> {
            return rowsToColumns(initial)
        }
        private fun southProjection(initial: List<String>): List<String> {
            return northProjection(initial).map { it.reversed() }
        }
        private fun westProjection(initial: List<String>): List<String> {
            return initial
        }
        private fun eastProjection(initial: List<String>): List<String> {
            return westProjection(initial).map { it.reversed() }
        }
    }
}
