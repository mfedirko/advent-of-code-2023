package io.mfedirko.aoc

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


abstract class SolutionTest<CLZ, OUT> where CLZ : Solution<OUT> {
    abstract val solution: Solution<OUT>
    abstract val expectedTestPartOne: OUT
    abstract val expectedPartOne: OUT
    abstract val expectedTestPartTwo: OUT
    abstract val expectedPartTwo: OUT

    private val solutionFileName = solution::class.simpleName!!.lowercase()
    open var testPartOneInput: String = "$solutionFileName-test"
    open var testPartTwoInput: String = "$solutionFileName-test"
    open var partOneInput: String = solutionFileName
    open var partTwoInput: String = solutionFileName


    @Test
    fun testPartOne() {
        Assertions.assertEquals(expectedTestPartOne, solution.partOne(InputReaderUtil.read(testPartOneInput)))
    }

    @Test
    fun partOne() {
        Assertions.assertEquals(expectedPartOne, solution.partOne(InputReaderUtil.read(partOneInput)))
    }

    @Test
    fun testPartTwo() {
        Assertions.assertEquals(expectedTestPartTwo, solution.partTwo(InputReaderUtil.read(testPartTwoInput)))
    }


    @Test
    fun partTwo() {
        Assertions.assertEquals(expectedPartTwo, solution.partTwo(InputReaderUtil.read(partTwoInput)))
    }
}