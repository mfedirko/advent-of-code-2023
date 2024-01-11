package io.mfedirko.aoc.day17

import io.mfedirko.aoc.Coord
import io.mfedirko.aoc.Direction
import io.mfedirko.aoc.Solution
import java.util.*


object Day17 : Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return parse(input).let { minCostPath(it) }
    }

    override fun partTwo(input: Sequence<String>): Int {
        return parse(input).let { minCostPath(it, minSteps = 4, maxSteps = 10) }
    }

    private fun parse(input: Sequence<String>): Array<IntArray> {
        return input.mapIndexed { i, _ ->
            input.elementAt(i).mapIndexed { _, ch -> ch - '0' }.toIntArray()
        }.toList().toTypedArray()
    }

    data class State(val coord: Coord<HeatCoord>, val dir: Direction, val cost: Int) {
    }




    // Dijkstra's algorithm
    private fun minCostPath(heatCosts: Array<IntArray>, minSteps: Int = 1, maxSteps: Int = 3): Int {
        val n = heatCosts.size
        val m = heatCosts[0].size
        val visited = mutableSetOf<Pair<Coord<HeatCoord>, Direction>>()
        val queue = PriorityQueue<State>(n * m, compareBy { it.cost })
        val start = HeatCoord(0, 0)
        queue.addAll(setOf(
            State(start, Direction.RIGHT, 0),
            State(start, Direction.DOWN, 0),
        ))

        while (queue.isNotEmpty()) {
            val (curr, dir, currCost) = queue.poll()
            if (curr.y == n - 1 && curr.x == m - 1) {
                return currCost
            }
            if (visited.contains(curr to dir)) continue
            visited.add(curr to dir)
            for (nextDir in setOf(dir.turnLeft(), dir.turnRight())) {
                for (steps in (1..maxSteps)) {
                    val neighbor = curr.neighborTo(nextDir, steps)
                    if (!neighbor.isInsideGrid(heatCosts)) continue
                    if (visited.contains(neighbor to nextDir)) continue

                    val alt = curr.allNeighborsTo(nextDir, steps).fold(currCost) {
                        sum, neighb -> sum + heatCosts[neighb.y][neighb.x]
                    }
                    if (steps >= minSteps) {
                        val nextState = State(neighbor, nextDir, alt)
                        queue.remove(nextState)
                        queue.add(nextState)
                    }
                }
            }
        }
        error("Did not arrive at end node")
    }

    private fun print(cost: Array<IntArray>) {
        cost.forEach { row -> println(row.joinToString(" ")) }
    }

    fun Coord<HeatCoord>.isInsideGrid(grid: Array<IntArray>): Boolean {
        return x >= 0 && y >= 0 && x < grid[0].size && y < grid.size
    }

    fun Coord<HeatCoord>.neighborTo(dir: Direction, steps: Int): Coord<HeatCoord> {
        return when(dir) {
            Direction.RIGHT -> HeatCoord(x + steps, y)
            Direction.LEFT -> HeatCoord(x - steps, y)
            Direction.DOWN -> HeatCoord(x, y + steps)
            Direction.UP -> HeatCoord(x, y - steps)
        }
    }

    fun Coord<HeatCoord>.allNeighborsTo(dir: Direction, steps: Int): Set<Coord<HeatCoord>> {
        return when(dir) {
            Direction.RIGHT -> (1..steps).map { i -> HeatCoord(x + i, y) }.toSet()
            Direction.LEFT -> (1..steps).map { i -> HeatCoord(x - i, y) }.toSet()
            Direction.DOWN -> (1..steps).map { i -> HeatCoord(x, y + i) }.toSet()
            Direction.UP -> (1..steps).map { i -> HeatCoord(x, y - i) }.toSet()
        }
    }

    class HeatCoord(x: Int, y: Int) : Coord<HeatCoord>(x, y) {
    }

}