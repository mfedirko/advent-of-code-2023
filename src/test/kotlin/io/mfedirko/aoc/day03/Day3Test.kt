package io.mfedirko.aoc.day03

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day3Test : SolutionTest<Day3, Int>() {
    override val solution: Solution<Int>
        get() = Day3
    override val expectedTestPartOne: Int
        get() = 4361
    override val expectedPartOne: Int
        get() = 557705
    override val expectedTestPartTwo: Int
        get() = 467835
    override val expectedPartTwo: Int
        get() = 84266818
}