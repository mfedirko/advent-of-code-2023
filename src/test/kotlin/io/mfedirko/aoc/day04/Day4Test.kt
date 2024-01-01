package io.mfedirko.aoc.day04

import io.mfedirko.aoc.InputReaderUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day4Test {
    @Test
    fun testPartOne() {
        assertEquals(13, Day4.partOne(InputReaderUtil.read("day4-test")))
    }

    @Test
    fun partOne() {
        assertEquals(21158, Day4.partOne(InputReaderUtil.read("day4")))
    }

    @Test
    fun testPartTwo() {
        assertEquals(30, Day4.partTwo(InputReaderUtil.read("day4-test")))
    }


    @Test
    fun partTwo() {
        assertEquals(6050769, Day4.partTwo(InputReaderUtil.read("day4")))
    }
}