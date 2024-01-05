package io.mfedirko.aoc.day12

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day12Test: SolutionTest<Day12, Long>() {
    override val solution: Solution<Long>
        get() = Day12
    override val expectedTestPartOne: Long
        get() = 21
    override val expectedPartOne: Long
        get() = 7361
    override val expectedTestPartTwo: Long
        get() = 525152
    override val expectedPartTwo: Long
        get() = 83317216247365

}