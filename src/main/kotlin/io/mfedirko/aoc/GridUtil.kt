package io.mfedirko.aoc

object GridUtil {
    fun columns(grid: List<String>): List<String> {
        return grid[0].indices.map { i ->
            grid.map { row -> row[i] }.joinToString("")
        }
    }
}