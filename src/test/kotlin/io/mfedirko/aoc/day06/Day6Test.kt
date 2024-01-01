package io.mfedirko.aoc.day06

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day6Test : SolutionTest<Day6, Long>() {
    override val solution: Solution<Long>
        get() = Day6
    override val expectedTestPartOne: Long
        get() = 288
    override val expectedPartOne: Long
        get() = 2612736
    override val expectedTestPartTwo: Long
        get() = 71503
    override val expectedPartTwo: Long
        get() = 29891250

}