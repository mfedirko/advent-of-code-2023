package io.mfedirko.aoc.day15

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest
import org.junit.jupiter.api.Assertions.*

internal class Day15Test : SolutionTest<Day15, Int>() {
    override val solution: Solution<Int>
        get() = Day15
    override val expectedTestPartOne: Int
        get() = 1320
    override val expectedPartOne: Int
        get() = 513172
    override val expectedTestPartTwo: Int
        get() = 145
    override val expectedPartTwo: Int
        get() = 237806

}