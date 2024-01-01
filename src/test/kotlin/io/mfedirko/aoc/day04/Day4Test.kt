package io.mfedirko.aoc.day04

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day4Test : SolutionTest<Day4, Int>() {
    override val solution: Solution<Int>
        get() = Day4
    override val expectedTestPartOne: Int
        get() = 13
    override val expectedPartOne: Int
        get() = 21158
    override val expectedTestPartTwo: Int
        get() = 30
    override val expectedPartTwo: Int
        get() = 6050769
}