package io.mfedirko.aoc.day01

import io.mfedirko.aoc.InputReaderUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day1Test {

    @Test
    fun testPartOne() {
        assertEquals(142, Day1.partOne(InputReaderUtil.read("day1-test1")))
    }

    @Test
    fun partOne() {
        assertEquals(55208, Day1.partOne(InputReaderUtil.read("day1")))
    }

    @Test
    fun testPartTwo() {
        assertEquals(281, Day1.partTwo(InputReaderUtil.read("day1-test2")))
    }


    @Test
    fun partTwo() {
        assertEquals(54578, Day1.partTwo(InputReaderUtil.read("day1")))
    }
}