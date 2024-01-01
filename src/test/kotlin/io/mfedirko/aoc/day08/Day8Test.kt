package io.mfedirko.aoc.day08

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day8Test : SolutionTest<Day8, Long>() {
    override val solution: Solution<Long>
        get() = Day8
    override val expectedTestPartOne: Long
        get() = 6
    override val expectedPartOne: Long
        get() = 21389
    override val expectedTestPartTwo: Long
        get() = 6
    override val expectedPartTwo: Long
        get() = 21083806112641

    override var testPartTwoInput: String
        get() = "day8-test2"
        set(value) {}
}