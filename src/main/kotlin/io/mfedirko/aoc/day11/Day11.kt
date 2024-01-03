package io.mfedirko.aoc.day11

import io.mfedirko.aoc.Solution
import kotlin.math.abs

private const val GALAXY_SYMBOL = '#'
private const val SPACE_SYMBOL = '.'

object Day11: Solution<Long> {
    override fun partOne(input: Sequence<String>): Long {
        return input.toList()
            .let { expandEmptySpace(it) }
            .let { findGalaxies(it) }
            .let { allCombinations(it) }
            .fold(0) { acc, pair ->
                acc + pair.first.manhattanDistanceTo(pair.second)
            }
    }

    override fun partTwo(input: Sequence<String>): Long {
        val image = input.toList()
        return image
            .let { findGalaxies(it) }
            .let { addEmptySpace(it, emptyRows(image), emptyColumns(image), expansionFactor = 1_000_000) }
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

    private fun expandEmptySpace(image: List<String>): List<String> {
        val emptyColumns = emptyColumns(image)
        val emptyRows = emptyRows(image)
        return image.flatMapIndexed { i, row ->
                row.mapIndexed { j, c ->
                        if (emptyColumns.contains(j)) "${c}${c}" else "$c" }.joinToString("") // expand col
                    .let {
                        if (emptyRows.contains(i)) listOf(it, it) else listOf(it)  // expand row
                    }
        }
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

    private fun allCombinations(coords: List<Coord>): Set<Pair<Coord, Coord>> {
        return coords.flatMap {first ->
                        coords.filter {second -> second != first}
                          .map {second ->
                                if (first < second) first to second else second to first
                          }
                    }.toSet()
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
