package io.mfedirko.aoc.day01

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day1Test : SolutionTest<Day1, Int>() {
    override val solution: Solution<Int>
        get() = Day1
    override val expectedTestPartOne: Int
        get() = 142
    override val expectedPartOne: Int
        get() = 55208
    override val expectedTestPartTwo: Int
        get() = 281
    override val expectedPartTwo: Int
        get() = 54578
    override var testPartOneInput: String
        get() = "day1-test1"
        set(value) {}
    override var testPartTwoInput: String
        get() = "day1-test2"
        set(value) {}
}