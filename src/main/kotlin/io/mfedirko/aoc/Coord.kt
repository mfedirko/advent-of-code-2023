package io.mfedirko.aoc

import java.lang.IllegalStateException


open class Coord<T : Coord<T>>(val x: Int, val y: Int) {

    fun directionTo(other: Coord<T>): Direction {
        return when {
            other.y < y -> Direction.UP
            other.y > y -> Direction.DOWN
            other.x < x -> Direction.LEFT
            other.x > x -> Direction.RIGHT
            else -> throw IllegalStateException("Invalid direction from $this to $other")
        }
    }


    fun neighbors(): Set<Coord<T>> {
        return setOf(
            Coord(x - 1, y),
            Coord(x + 1, y),
            Coord(x, y - 1),
            Coord(x, y + 1)
        )
    }

    fun <A> isInBounds(grid: List<List<A>>): Boolean {
        return x >= 0 && x < grid[0].size && y >= 0 && y < grid.size
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coord<*>

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "($x, $y)"
    }
}