package io.mfedirko.aoc.day13

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest
import org.junit.jupiter.api.Assertions.*

internal class Day13Test: SolutionTest<Day13, Int>() {
    override val solution: Solution<Int>
        get() = Day13
    override val expectedTestPartOne: Int
        get() = 405
    override val expectedPartOne: Int
        get() = 31265
    override val expectedTestPartTwo: Int
        get() = 400
    override val expectedPartTwo: Int
        get() = 39359

}