package io.mfedirko.aoc.day14

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest
import org.junit.jupiter.api.Assertions.*

internal class Day14Test: SolutionTest<Day14, Int>() {
    override val solution: Solution<Int>
        get() = Day14
    override val expectedTestPartOne: Int
        get() = 136
    override val expectedPartOne: Int
        get() = 109833
    override val expectedTestPartTwo: Int
        get() =  64
    override val expectedPartTwo: Int
        get() = 99875

}