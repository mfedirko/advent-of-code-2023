package io.mfedirko.aoc.day13

import io.mfedirko.aoc.GridUtil.rowsToColumns
import io.mfedirko.aoc.Solution

object Day13: Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return parse(input)
            .sumOf { columnOfReflection(it) + 100 * rowOfReflection(it) }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return parse(input)
            .sumOf { columnOfReflection(it, smudge = true) + 100 * rowOfReflection(it, smudge = true) }
    }

    private fun rowOfReflection(grid: List<String>, smudge: Boolean = false): Int {
        return reflectionPoint(grid, smudge) ?: 0
    }

    private fun columnOfReflection(grid: List<String>, smudge: Boolean = false): Int {
        val cols = rowsToColumns(grid)
        return reflectionPoint(cols, smudge) ?: 0
    }

    private fun reflectionPoint(data: List<String>, smudge: Boolean): Int? {
        val end = data.size - 1
        return (data.size / 2  downTo 1)  // left aligned
            .firstOrNull { i -> isReflectionPoint(i, alignTo = 0, data, smudge) }
            ?.let { if (data.size % 2 == 1) it else it + 1 }
         ?: (data.size / 2  until  end) // right aligned
            .firstOrNull { i -> isReflectionPoint(i, alignTo = end, data, smudge) }
             ?.let { if (data.size % 2 == 0) it else it + 1 }
    }

    private fun isReflectionPoint(col: Int, alignTo: Int, data: List<String>, smudge: Boolean): Boolean {
        val len = data.size - 1
        var start = if (alignTo == 0) 0 else col * 2  - len
        var end = if (alignTo == len) len else col * 2

        if ((end - start + 1) % 2 == 1) {
            if (alignTo == 0) {
                end--
            } else {
                start++
            }
        }
        if (start >= end || end >= data.size || start < 0) {
            error("Invalid bounds: [$start, $end] for length ${data.size}")
        }

        val midpoint = start + (end - start) / 2
        val allowedErrors = if (smudge) 1 else 0
        return (start..midpoint).sumOf { i ->
            data[i].indices.count { c -> data[i][c] != data[end - i + start][c] }
        } == allowedErrors
    }



    private fun parse(input: Sequence<String>): List<List<String>> {
       return input.joinToString("\n").split("\n\n")
           .map { it.split("\n") }
    }



}
