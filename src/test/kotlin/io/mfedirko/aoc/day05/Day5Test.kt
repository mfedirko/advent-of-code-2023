package io.mfedirko.aoc.day05

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day5Test : SolutionTest<Day5, Long>() {
    override val solution: Solution<Long>
        get() = Day5
    override val expectedTestPartOne: Long
        get() = 35
    override val expectedPartOne: Long
        get() = 324724204
    override val expectedTestPartTwo: Long
        get() = 46
    override val expectedPartTwo: Long
        get() = 104070862

}