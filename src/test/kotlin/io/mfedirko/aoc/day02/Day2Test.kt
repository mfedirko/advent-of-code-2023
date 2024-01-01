package io.mfedirko.aoc.day02

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day2Test: SolutionTest<Day2, Int>() {
    override val solution: Solution<Int>
        get() = Day2
    override val expectedTestPartOne: Int
        get() = 8
    override val expectedPartOne: Int
        get() = 2810
    override val expectedTestPartTwo: Int
        get() = 2286
    override val expectedPartTwo: Int
        get() = 69110
}