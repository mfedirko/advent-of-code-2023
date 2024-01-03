package io.mfedirko.aoc.day11

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day11Test: SolutionTest<Day11, Long>() {
    override val solution: Solution<Long>
        get() = Day11
    override val expectedTestPartOne: Long
        get() = 374
    override val expectedPartOne: Long
        get() = 9445168
    override val expectedTestPartTwo: Long
        get() = 82000210
    override val expectedPartTwo: Long
        get() = 742305960572

}