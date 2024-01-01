package io.mfedirko.aoc

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


abstract class SolutionTest<I, O> where I : Solution<O> {
    abstract val solution: Solution<O>
    abstract val expectedTestPartOne: O
    abstract val expectedPartOne: O
    abstract val expectedTestPartTwo: O
    abstract val expectedPartTwo: O

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