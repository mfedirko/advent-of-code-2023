package io.mfedirko.aoc.day03

import io.mfedirko.aoc.InputReaderUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day3Test {
    @Test
    fun testPartOne() {
        assertEquals(4361, Day3.partOne(InputReaderUtil.read("day3-test")))
    }

    @Test
    fun partOne() {
        assertEquals(557705, Day3.partOne(InputReaderUtil.read("day3")))
    }

    @Test
    fun testPartTwo() {
        assertEquals(467835, Day3.partTwo(InputReaderUtil.read("day3-test")))
    }


    @Test
    fun partTwo() {
        assertEquals(84266818, Day3.partTwo(InputReaderUtil.read("day3")))
    }
}