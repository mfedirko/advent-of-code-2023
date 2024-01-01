package io.mfedirko.aoc.day05

import io.mfedirko.aoc.InputReaderUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day5Test {
    @Test
    fun testPartOne() {
        assertEquals(35, Day5.partOne(InputReaderUtil.read("day5-test")))
    }

    @Test
    fun partOne() {
        assertEquals(324724204, Day5.partOne(InputReaderUtil.read("day5")))
    }

    @Test
    fun testPartTwo() {
        assertEquals(46, Day5.partTwo(InputReaderUtil.read("day5-test")))
    }


    @Test
    fun partTwo() {
        assertEquals(104070862, Day5.partTwo(InputReaderUtil.read("day5")))
    }
}