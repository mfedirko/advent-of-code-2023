package io.mfedirko.aoc.day16

import io.mfedirko.aoc.Solution
import java.util.*

object Day16: Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return input.toList()
            .let { parseTiles(it) }
            .let { Grid(it).countEnergizedTiles() }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return input.toList()
            .let { parseTiles(it) }
            .let { Grid(it).countEnergizedTilesPart2() }
    }

    private fun parseTiles(data: List<String>): List<List<Tile>> {
        return data.indices.map { i ->
            data[i].indices.map { j ->
                Tile(j, i, Shape.fromValue(data[i][j]))
            }.toList()
        }.toList()
    }

    class Grid(private val tiles: List<List<Tile>>) {

        private fun moveBfs(start: Tile, direction: Direction): Set<Tile> {
            val queue = LinkedList<Pair<Tile, Direction>>().apply {
                add(start to direction)
            }
            val visited = mutableSetOf<Pair<Tile, Direction>>()

            while (queue.isNotEmpty()) {
                val (tile, dir) = queue.poll()
                visited.add(tile to dir)
                for (newDir in tile.redirect(dir)) {
                    val (y, x) = tile.y to tile.x
                    val (y2, x2) = newDir.proceed(y, x)
                    if (y2 < 0 || y2 >= tiles.size || x2 < 0 || x2 >= tiles[0].size) {
                        continue
                    }
                    val next = tiles[y2][x2] to newDir
                    if (visited.contains(next)) {
                        continue
                    }
                    queue.add(next)
                }
            }
            return visited.map { (tile, _) -> tile }.toSet()
        }

        fun countEnergizedTiles(start: Tile = tiles[0][0], direction: Direction = Direction.RIGHT): Int {
            return moveBfs(start, direction).size
        }

        fun countEnergizedTilesPart2(): Int {
            val leftWall = tiles.map { row -> row[0] }.map { it to Direction.RIGHT }
            val rightWall = tiles.map { row -> row[row.size - 1] }.map { it to Direction.LEFT }
            val topWall = tiles[0].map { it to Direction.DOWN }
            val bottomWall = tiles[tiles.size - 1].map { it to Direction.UP }

            return leftWall.union(rightWall).union(topWall.union(bottomWall))
                .maxOf { (tile, dir) ->
                    countEnergizedTiles(tile, dir)
                }
        }
    }

    class Tile(val x: Int, val y: Int, private val shape: Shape) {
        fun redirect(direction: Direction): Sequence<Direction> {
            return shape.redirect(direction)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Tile

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }
    }

    enum class Shape(val symbol: Char) {
        EMPTY('.'),
        BACK_MIRROR('\\'),
        FORWARD_MIRROR('/'),
        VERTICAL_MIRROR('|'),
        HORIZONTAL_MIRROR('-');

        companion object {
            fun fromValue(symbol: Char): Shape {
                return Shape.values().first { s -> s.symbol == symbol }
            }
        }
    }

    private fun Shape.redirect(dir: Direction): Sequence<Direction> {
        return when(this) {
            Shape.EMPTY -> sequenceOf(dir)
            Shape.BACK_MIRROR -> when(dir) {
                Direction.RIGHT -> sequenceOf(Direction.DOWN)
                Direction.LEFT -> sequenceOf(Direction.UP)
                Direction.DOWN -> sequenceOf(Direction.RIGHT)
                Direction.UP -> sequenceOf(Direction.LEFT)
            }
            Shape.FORWARD_MIRROR -> when(dir) {
                Direction.RIGHT -> sequenceOf(Direction.UP)
                Direction.LEFT -> sequenceOf(Direction.DOWN)
                Direction.DOWN -> sequenceOf(Direction.LEFT)
                Direction.UP -> sequenceOf(Direction.RIGHT)
            }
            Shape.VERTICAL_MIRROR -> when(dir) {
                Direction.RIGHT -> sequenceOf(Direction.DOWN, Direction.UP)
                Direction.LEFT -> sequenceOf(Direction.DOWN, Direction.UP)
                else -> sequenceOf(dir)
            }
            Shape.HORIZONTAL_MIRROR -> when(dir) {
                Direction.DOWN -> sequenceOf(Direction.LEFT, Direction.RIGHT)
                Direction.UP -> sequenceOf(Direction.LEFT, Direction.RIGHT)
                else -> sequenceOf(dir)
            }
        }
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
    private fun Direction.proceed(y: Int, x: Int): Pair<Int, Int> { // y, x
        return when(this) {
            Direction.LEFT -> y to x - 1
            Direction.RIGHT -> y to x + 1
            Direction.UP -> y - 1 to x
            Direction.DOWN -> y + 1 to x
        }
    }
}