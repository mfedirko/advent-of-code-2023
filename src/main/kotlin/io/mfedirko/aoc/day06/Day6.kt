package io.mfedirko.aoc.day06

import io.mfedirko.aoc.Solution


/**
 * https://adventofcode.com/2023/day/6
 */
object Day6 : Solution<Long> {
    val numRegex = "\\d+".toRegex()

    override fun partOne(input: Sequence<String>): Long {
        return input
                .toList()
                .let { parse(it).waysToWin() }
                .fold(1) { acc, next -> acc * next }
    }

    override fun partTwo(input: Sequence<String>): Long {
        return input
                .toList()
                .let { parseV2(it).waysToWin() }.first().toLong()
    }

    private fun parse(input: List<String>): MarginOfError {
        val times: List<Long> = numRegex.findAll(input[0]).map { it.value.toLong() }.toList()
        val records: List<Long> = numRegex.findAll(input[1]).map { it.value.toLong() }.toList()
        return MarginOfError(times, records)
    }

    private fun parseV2(input: List<String>): MarginOfError {
        val time: Long = numRegex.findAll(input[0]).fold("") { acc, match -> acc + match.value }.toLong()
        val record: Long = numRegex.findAll(input[1]).fold("") { acc, match -> acc + match.value }.toLong()
        return MarginOfError(listOf(time), listOf(record))
    }

    class MarginOfError(private val times: List<Long>, private val records: List<Long>) {
        private val count = times.size

        fun waysToWin(): Sequence<Int> {
            return (0 until count).asSequence()
                .map { waysToWin(times[it], records[it]) }

        }

        companion object {
            fun waysToWin(time: Long, record: Long): Int {
                return (1 until time).count { wait -> wait * time - wait * wait > record }
            }
        }
    }
}
