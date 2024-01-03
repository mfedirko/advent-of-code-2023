package io.mfedirko.aoc.day10

import io.mfedirko.aoc.Solution
import java.lang.IllegalStateException
import java.util.LinkedList
import java.util.Queue
import kotlin.math.max

object Day10 : Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return input.toList()
            .let { Grid(it).maxDistance() }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return input.toList()
            .let { Grid(it).areaInsideLoop() }
    }

    class Grid(private val data: List<String>) {
        private val yMax: Int = data.size - 1
        private val xMax: Int = data[0].length - 1
        private val start: Coord by findStart()

        private fun findStart() = lazy { // (y, x)
            for (y in 0..yMax) {
                for (x in 0..xMax) {
                    if (data[y][x] == 'S') {
                        return@lazy Coord(y = y, x = x, dist = 0)
                    }
                }
            }
            throw IllegalStateException("No start (S) found in grid")
        }

        fun maxDistance(): Int {
            val visited = mutableSetOf<Coord>()
            val queue: Queue<Coord> = LinkedList()
            queue.add(start)

            var maxDist = 0
            while (queue.isNotEmpty()) {
                val coord = queue.poll()
                maxDist = max(coord.dist, maxDist)
                visited.add(coord)
                coord.neighbors
                    .filter { !visited.contains(it) }
                    .filter { canReach(it, from = coord) }
                    .forEach { queue.add(it) }
            }
            return maxDist
        }

        fun areaInsideLoop(): Int {
            TODO("Not yet implemented")
        }

        private fun canReach(it: Coord, from: Coord): Boolean {
            val fromShape = Shape.fromSymbol(from.symbol)
            val toShape = Shape.fromSymbol(it.symbol)
            return fromShape.canExitTo(from.directionTo(it)) && toShape.canEnterFrom(it.directionTo(from))
        }

        inner class Coord(val y: Int, val x: Int, val dist: Int) {
            val neighbors: Set<Coord> by lazy {
                sequenceOf(y - 1 to x, y + 1 to x, y to x - 1, y to x + 1)
                    .filter { !isOutOfBounds(it) }
                    .map { (y, x) -> Coord(y, x, dist + 1) }
                    .toSet()
            }
            val symbol = data[y][x]
            private fun isOutOfBounds(pair: Pair<Int, Int>): Boolean {
                val (y, x) = pair
                return yMax < y || xMax < x || x < 0 || y < 0
            }

            fun directionTo(coord: Coord): Direction {
                return when {
                    coord.y < y -> Direction.NORTH
                    coord.y > y -> Direction.SOUTH
                    coord.x < x -> Direction.WEST
                    coord.x > x -> Direction.EAST
                    else -> throw IllegalStateException("Invalid direction from $this to $coord")
                }
            }

            override fun equals(other: Any?): Boolean {
                return other is Coord && other.y == y && other.x == x
            }

            override fun hashCode(): Int {
                var result = y
                result = 31 * result + x
                return result
            }

        }
    }

    sealed class Shape {
        abstract fun canExitTo(direction: Direction): Boolean
        open fun canEnterFrom(direction: Direction) = canExitTo(direction)

        companion object {
            fun fromSymbol(symbol: Char): Shape {
                return when(symbol) {
                    '|' -> VerticalPipe
                    '-' -> HorizontalPipe
                    'L' -> NEBend
                    'J' -> NWBend
                    '7' -> SWBend
                    'F' -> SEBend
                    '.' -> Ground
                    'S' -> Start
                    else -> throw IllegalStateException("Unknown shape: $symbol")
                }
            }
        }
    }
    object VerticalPipe : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.NORTH || direction == Direction.SOUTH
        }
    }
    object HorizontalPipe : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.WEST || direction == Direction.EAST
        }
    }
    object NEBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.NORTH || direction == Direction.EAST
        }
    }
    object NWBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.NORTH || direction == Direction.WEST
        }
    }
    object SWBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.SOUTH || direction == Direction.WEST
        }
    }
    object SEBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.SOUTH || direction == Direction.EAST
        }
    }

    object Ground : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return false
        }
    }
    object Start : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return true
        }
    }

    enum class Direction {
        WEST, EAST, NORTH, SOUTH;
    }
}