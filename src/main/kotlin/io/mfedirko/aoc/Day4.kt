package io.mfedirko.aoc

import kotlin.math.pow
import kotlin.properties.Delegates

object Day4 {

    fun partOne(): Int {
        return InputReaderUtil.read("day4")
            .map { parseScratchCard(it).pointValue }
            .sum()
    }
    fun partTwo(): Int {
        return InputReaderUtil.read("day4")
            .map { parseScratchCard(it) }
            .toList()
            .sumOf { it.countExpanded + 1 }
    }

    private fun parseScratchCard(line: String): ScratchCard {
        val winningAndActual = line.substringAfter(":").trim().split("|")
        val id = line.substring("Card ".length).substringBefore(":").trim().toInt()
        val winningNums: Set<Int> = parseNums(winningAndActual[0])
        val actualNums: Set<Int> = parseNums(winningAndActual[1])
        return ScratchCard(id, winningNums, actualNums)
    }

    private fun parseNums(line: String): Set<Int> {
        return line.trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
    }

    class ScratchCard(val id: Int, winningNums: Set<Int>, actualNums: Set<Int>) {
        private val countWinning: Int by lazy {
            winningNums.intersect(actualNums).size
        }
        val pointValue: Int by lazy {
            if (countWinning == 0) 0
            else 2.0.pow(countWinning - 1).toInt()
        }
        val countExpanded: Int by lazy {
            countWinning + (id + 1..id + countWinning).sumOf { cards[it]!!.countExpanded  }
        }

        init {
            cards[id] = this
        }

        companion object CardRegistry {
            val cards: MutableMap<Int, ScratchCard> = mutableMapOf()
        }
    }




}

fun main(args: Array<String>) {
    println(Day4.partTwo())
}