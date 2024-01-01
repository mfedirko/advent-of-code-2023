package io.mfedirko.aoc.day02

import io.mfedirko.aoc.InputReaderUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day2Test {
    private val actualCounts = mapOf("red" to 12, "green" to 13, "blue" to 14)

    @Test
    fun testPartOne() {
        assertEquals(8, Day2.partOne(InputReaderUtil.read("day2-test"), actualCounts))
    }

    @Test
    fun partOne() {
        assertEquals(2810, Day2.partOne(InputReaderUtil.read("day2"), actualCounts))
    }

    @Test
    fun testPartTwo() {
        assertEquals(2286, Day2.partTwo(InputReaderUtil.read("day2-test")))
    }


    @Test
    fun partTwo() {
        assertEquals(69110, Day2.partTwo(InputReaderUtil.read("day2")))
    }
}