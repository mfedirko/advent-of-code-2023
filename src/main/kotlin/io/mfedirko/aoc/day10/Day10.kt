package io.mfedirko.aoc.day10

import io.mfedirko.aoc.Solution
import java.lang.IllegalStateException
import java.util.LinkedList
import java.util.Queue
import kotlin.math.max

object Day10 : Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return input.toList()
            .let { Grid(it).maxDistance().first }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return input.toList()
            .let { Grid(it, expand = true).areaInsideLoop() }
    }

    class Grid(data: List<String>, expand: Boolean = false) {
        private val start: Coord by findStart()
        private val coords: List<List<Coord>> = mapDataToCoords(data, expand)

        private fun neighbors(coord: Coord): Set<Coord> {
            with(coord) {
                return sequenceOf(y - 1 to x, y + 1 to x, y to x - 1, y to x + 1)
                    .filter { !isOutOfBounds(it) }
                    .map { (y, x) -> coords[y][x] }
                    .toSet()
            }
        }
        private fun isOutOfBounds(pair: Pair<Int, Int>): Boolean {
            val (y, x) = pair
            return coords.size <= y || coords[0].size <= x || x < 0 || y < 0
        }


        private fun findStart() = lazy {
            coords.flatten().first { it.shape == Start }
        }

        private fun mapDataToCoords(data: List<String>, expand: Boolean): List<List<Coord>> {
            return if (expand) {
                mapDataPartTwo(data)
            } else {
                mapDataPartOne(data)
            }
        }

        private fun mapDataPartOne(data: List<String>): List<List<Coord>> {
            return (data.indices).map { y ->
                (0 until data[0].length).map { x ->
                    val shape = fromSymbol(data[y][x])
                    Coord(y = y, x = x, shape = shape, dist = 0)
                }
            }
        }

        private fun mapDataPartTwo(data: List<String>): List<List<Coord>> {
            val expansions = mapOf(
                '|' to listOf("|*", "|*"), // vertical, horizontal replacements
                '-' to listOf("--", "**"),
                'L' to listOf("L-", "**"),
                'J' to listOf("J*", "**"),
                '7' to listOf("7*", "|*"),
                'F' to listOf("F-", "|*"),
                '.' to listOf(".*", "**"),
                'S' to listOf("SS", "SS")
            )
            val expand: List<String> = data.flatMap { row ->
                val upper = row.map { expansions[it]!![0] }.joinToString("")
                val lower = row.map { expansions[it]!![1] }.joinToString("")
                sequenceOf(upper, lower)
            }
            return mapDataPartOne(expand)
        }

        fun areaInsideLoop(): Int {
            val (_, visitedPipes) = maxDistance()
            val visited = mutableSetOf<Coord>().apply { addAll(visitedPipes) }

            val queue = LinkedList<Coord>()
            for (boundary in boundary().filter { !visited.contains(it) }) {
                visited.add(boundary)
                queue.add(boundary)
            }
            while (queue.isNotEmpty()) {
                val coord = queue.poll()
                val neighbors = neighbors(coord)
                neighbors.filter { !visited.contains(it) }
                    .forEach {
                        queue.add(it)
                        visited.add(it)
                    }
            }
//            printGrid(visited)

            val realCoords: (Coord) -> Boolean = { it.y % 2 == 0 && it.x % 2 == 0 }
            val countAll = coords.flatten().count(realCoords)
            val countVisited = visited.count(realCoords)
            return countAll - countVisited
        }

        private fun boundary(): Set<Coord> {
            val top = coords.first()
            val bottom = coords.last()
            val left = coords.indices.map { coords[it].first() }
            val right = coords.indices.map { coords[it].last() }
            return top.union(bottom).union(left).union(right)
        }

        private fun printGrid(visited: Collection<Coord> = setOf()) {
            coords.forEach { row ->
                row.filter { it.y % 2 == 0 && it.x % 2 == 0 }.forEach {coord ->
                    if (visited.contains(coord) && coord.shape is Ground || coord.shape is FakeGround) {
                        print(' ')
                    } else if (!visited.contains(coord) && coord.x % 2 == 0 && coord.shape !is Ground) {
                        print('?')
                    } else {
                        print(coord.shape)
                    }
                }
                println()
            }
        }


        private fun fromSymbol(symbol: Char): Shape {
            return when(symbol) {
                '|' -> VerticalPipe
                '-' -> HorizontalPipe
                'L' -> NEBend
                'J' -> NWBend
                '7' -> SWBend
                'F' -> SEBend
                '.' -> Ground
                '*' -> FakeGround
                'S' -> Start
                else -> throw IllegalStateException("Unknown shape: $symbol")
            }
        }

        fun maxDistance(): Pair<Int, Set<Coord>> {
            val queue: Queue<Coord> = LinkedList()
            val visitedPipes = mutableSetOf<Coord>()
            queue.add(start)

            var maxDist = 0
            while (queue.isNotEmpty()) {
                val coord = queue.poll()
                maxDist = max(coord.dist, maxDist)
                visitedPipes.add(coord)
                neighbors(coord)
                    .filter { !visitedPipes.contains(it) }
                    .filter { canReach(it, from = coord) }
                    .forEach {
                        it.dist = coord.dist + 1
                        queue.add(it)
                    }
            }
            return maxDist to visitedPipes
        }

        private fun canReach(it: Coord, from: Coord): Boolean {
            val fromShape = from.shape
            val toShape = it.shape
            return fromShape.canExitTo(from.directionTo(it)) && toShape.canEnterFrom(it.directionTo(from))
        }
    }

    class Coord(val y: Int, val x: Int, var dist: Int, val shape: Shape) {

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

    sealed class Shape {
        abstract fun canExitTo(direction: Direction): Boolean
        open fun canEnterFrom(direction: Direction) = canExitTo(direction)

    }
    object VerticalPipe : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.NORTH || direction == Direction.SOUTH
        }
        override fun toString(): String {
            return "|"
        }
    }
    object HorizontalPipe : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.WEST || direction == Direction.EAST
        }
        override fun toString(): String {
            return "-"
        }
    }
    object NEBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.NORTH || direction == Direction.EAST
        }
        override fun toString(): String {
            return "L"
        }
    }
    object NWBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.NORTH || direction == Direction.WEST
        }
        override fun toString(): String {
            return "J"
        }
    }
    object SWBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.SOUTH || direction == Direction.WEST
        }
        override fun toString(): String {
            return "7"
        }
    }
    object SEBend : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return direction == Direction.SOUTH || direction == Direction.EAST
        }
        override fun toString(): String {
            return "F"
        }
    }

    object Ground : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return false
        }

        override fun toString(): String {
            return "."
        }
    }

    object FakeGround : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return false
        }

        override fun toString(): String {
            return "*"
        }
    }
    object Start : Shape() {
        override fun canExitTo(direction: Direction): Boolean {
            return true
        }

        override fun toString(): String {
            return "S"
        }
    }

    enum class Direction {
        WEST, EAST, NORTH, SOUTH;
    }
}