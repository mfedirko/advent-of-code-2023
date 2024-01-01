package io.mfedirko.aoc

interface Solution<T> {
    fun partOne(input: Sequence<String>): T
    fun partTwo(input: Sequence<String>): T
}