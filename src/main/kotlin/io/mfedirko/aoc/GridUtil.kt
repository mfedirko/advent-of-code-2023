package io.mfedirko.aoc

object GridUtil {
    fun rowsToColumns(grid: List<String>): List<String> {
        return grid[0].indices.map { i ->
            grid.map { row -> row[i] }.joinToString("")
        }
    }

    fun columnsToRows(grid: List<String>): List<String> {
        return rowsToColumns(grid)
    }
}