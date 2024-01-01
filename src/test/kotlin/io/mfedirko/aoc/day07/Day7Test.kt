package io.mfedirko.aoc.day07

import io.mfedirko.aoc.InputReaderUtil
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day7Test {
    @Test
    fun testPartOne() {
        assertEquals(6440, Day7.partOne(InputReaderUtil.read("day7-test")))
    }

    @Test
    fun partOne() {
        assertEquals(247823654, Day7.partOne(InputReaderUtil.read("day7")))
    }

    @Test
    fun testPartTwo() {
        assertEquals(5905, Day7.partTwo(InputReaderUtil.read("day7-test")))
    }


    @Test
    fun partTwo() {
        assertEquals(245461700, Day7.partTwo(InputReaderUtil.read("day7")))
    }
}