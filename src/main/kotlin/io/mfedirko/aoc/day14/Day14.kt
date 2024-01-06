package io.mfedirko.aoc.day14

import io.mfedirko.aoc.GridUtil
import io.mfedirko.aoc.Solution

private const val GROUND = '.'
private const val CUBE_ROCK = '#'
private const val ROUND_ROCK = 'O'

object Day14: Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return input.toList()
            .let { Platform(it, useSpinCycle = false).totalLoad() }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return input.toList()
            .let { Platform(it, useSpinCycle = true).totalLoad() }
    }

    class Platform(private val initial: List<String>, private val useSpinCycle: Boolean) {
        fun totalLoad(): Int {
            val cols = northProjection(initial)
            return if (useSpinCycle) {
                val (_, sequence) = (0 until 300).fold((cols to mutableListOf<Int>())) { acc, _ ->
                    val (grid, seq) = acc
                    val nextGrid = spinCycle(grid)
                    val totalLoad = totalLoad(northProjection(nextGrid))
                    seq.add(totalLoad)
                    nextGrid to seq
                }
                deriveSequenceValue(sequence, 1_000_000_000)
            } else {
                val rows = northProjection(fallIntoPlaceAll(cols))
                totalLoad(rows)
            }
        }

        private fun deriveSequenceValue(sequence: List<Int>, targetIndex: Int): Int {
            val len = 5
            var i = 0
            var matchIndex = -1
            while (matchIndex < 0) {
                i += len
                matchIndex = sequence.indices.firstOrNull { index ->
                    index > i + len
                    && (index  until index + len).all { k -> sequence[k] == sequence[i + k - index]  }
                } ?: -1
            }

            val cycleLen = matchIndex - i
            val position = (targetIndex - i - 1) % cycleLen
            return sequence[i + position]
        }

        private fun totalLoad(grid: List<String>): Int {
            return grid.mapIndexed { i, row ->
                (grid.size - i) * row.count { it == ROUND_ROCK }
            }.sum()
        }

        private fun fallIntoPlaceAll(initial: List<String>): List<String> {
            return initial.map { fallIntoPlace(it) }.toList()
        }

        private fun spinCycle(initial: List<String>): List<String> {
            return initial
                .let { fallIntoPlaceAll(it) } // north
                .let { northProjection(it) }
                .let { fallIntoPlaceAll(it) } // west
                .let { southProjection(it) }
                .let { fallIntoPlaceAll(it) } // south
                .let { southProjection(eastProjection(it)) }
                .let { fallIntoPlaceAll(it) } // east
                .let { northProjection(southProjection(northProjection(it))) }  // return as north projection (columns)

        }

        private fun fallIntoPlace(col: String): String {
            if (col.isEmpty()) return col
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
            return GridUtil.columns(initial)
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
