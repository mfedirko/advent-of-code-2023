package io.mfedirko.aoc.day11

import io.mfedirko.aoc.CollectionUtil.allCombinations
import io.mfedirko.aoc.Solution
import kotlin.math.abs

private const val GALAXY_SYMBOL = '#'
private const val SPACE_SYMBOL = '.'

object Day11: Solution<Long> {
    override fun partOne(input: Sequence<String>): Long {
        return solve(input, 2)

    }

    override fun partTwo(input: Sequence<String>): Long {
        return solve(input, 1_000_000)
    }

    private fun solve(input: Sequence<String>, expansionFactor: Long): Long {
        val image = input.toList()
        return image
            .let { findGalaxies(it) }
            .let { addEmptySpace(it, emptyRows(image), emptyColumns(image), expansionFactor = expansionFactor) }
            .let { allCombinations(it) }
            .fold(0L) { acc, pair ->
                acc + pair.first.manhattanDistanceTo(pair.second)
            }
    }

    private fun addEmptySpace(coords: List<Coord>, emptyRows: Set<Int>, emptyColumns: Set<Int>, expansionFactor: Long): List<Coord> {
        return coords.map { coord ->
            var (y, x) = coord
            x += emptyColumns.filter { i -> i < x }.count() * (expansionFactor - 1)
            y += emptyRows.filter { i -> i < y }.count() * (expansionFactor - 1)
            Coord(y, x)
        }.toList()
    }

    private fun emptyColumns(image: List<String>): Set<Int> {
        return image[0].indices.filter { i -> image.all { row -> row[i] == SPACE_SYMBOL } }.toSet()
    }

    private fun emptyRows(image: List<String>): Set<Int> {
        return image.indices.filter { i -> image[i].all { it == SPACE_SYMBOL } }.toSet()
    }

    private fun findGalaxies(image: List<String>): List<Coord> {
        return image.indices.flatMap { y ->
            (0 until image[y].length).filter { x ->
                image[y][x] == GALAXY_SYMBOL
            }.map { x -> Coord(y.toLong(), x.toLong()) }
            .asSequence()
        }
    }

    internal data class Coord(val y: Long, val x: Long): Comparable<Coord> {
        fun manhattanDistanceTo(other: Coord): Long {
            return abs(other.y - y) + abs(other.x - x)
        }

        override fun compareTo(other: Coord): Int {
            return compareBy<Coord>({it.y }, {it.x}).compare(this, other)
        }


    }

}
