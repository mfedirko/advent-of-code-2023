package io.mfedirko.aoc.day07

import io.mfedirko.aoc.Solution
import io.mfedirko.aoc.SolutionTest

internal class Day7Test : SolutionTest<Day7, Int>() {
    override val solution: Solution<Int>
        get() = Day7
    override val expectedTestPartOne: Int
        get() = 6440
    override val expectedPartOne: Int
        get() = 247823654
    override val expectedTestPartTwo: Int
        get() = 5905
    override val expectedPartTwo: Int
        get() = 245461700

}