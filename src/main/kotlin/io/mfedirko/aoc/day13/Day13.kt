package io.mfedirko.aoc.day13

import io.mfedirko.aoc.Solution

object Day13: Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return parse(input)
            .sumOf { columnOfReflection(it) + 100 * rowOfReflection(it) }
    }

    override fun partTwo(input: Sequence<String>): Int {
        TODO("Not yet implemented")
    }

    private fun rowOfReflection(grid: List<String>): Int {
        return reflectionPoint(grid) ?: 0
    }

    private fun columnOfReflection(grid: List<String>): Int {
        val cols = columns(grid)
        return reflectionPoint(cols) ?: 0
    }

    private fun reflectionPoint(data: List<String>): Int? {
        val end = data.size - 1
        return (data.size / 2  downTo 1)  // left aligned
            .firstOrNull { i -> isReflectionPoint(i, alignTo = 0, data) }
            ?.let { if (data.size % 2 == 1) it else it + 1 }
         ?: (data.size / 2  until  end) // right aligned
            .firstOrNull { i -> isReflectionPoint(i, alignTo = end, data) }
             ?.let { if (data.size % 2 == 0) it else it + 1 }
    }

    private fun isReflectionPoint(col: Int, alignTo: Int, data: List<String>): Boolean {
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
        return (start..midpoint).all { i -> data[i] == data[end - i + start] }
    }


    private fun columns(grid: List<String>): List<String> {
        return grid[0].indices.map { i ->
            grid.map { row -> row[i] }.joinToString("")
        }
    }

    private fun parse(input: Sequence<String>): List<List<String>> {
       return input.joinToString("\n").split("\n\n")
           .map { it.split("\n") }
    }



}
