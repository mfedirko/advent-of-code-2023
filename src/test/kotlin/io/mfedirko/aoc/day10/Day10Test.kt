package io.mfedirko.aoc.day10

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest
import org.junit.jupiter.api.Assertions.*

internal class Day10Test : SolutionTest<Day10, Int>() {
    override val solution: Solution<Int>
        get() = Day10
    override val expectedTestPartOne: Int
        get() = 8
    override val expectedPartOne: Int
        get() = 6701
    override val expectedTestPartTwo: Int
        get() = 10
    override val expectedPartTwo: Int
        get() = 303

    override var testPartTwoInput: String
        get() = "day10-test2"
        set(value) {}
}