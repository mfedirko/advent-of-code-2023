package io.mfedirko.aoc.day06

import io.mfedirko.aoc.InputReaderUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day6Test {
    @Test
    fun testPartOne() {
        assertEquals(288, Day6.partOne(InputReaderUtil.read("day6-test")))
    }

    @Test
    fun partOne() {
        assertEquals(2612736, Day6.partOne(InputReaderUtil.read("day6")))
    }

    @Test
    fun testPartTwo() {
        assertEquals(71503, Day6.partTwo(InputReaderUtil.read("day6-test")))
    }


    @Test
    fun partTwo() {
        assertEquals(29891250, Day6.partTwo(InputReaderUtil.read("day6")))
    }
}