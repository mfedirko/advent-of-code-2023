package io.mfedirko.aoc.day05

import kotlin.properties.Delegates

internal class InputParser(private val seedsAsRange: Boolean) {
    private var seeds: List<Long> by Delegates.notNull()
    private var seedRanges: List<KeyRange> by Delegates.notNull()

    private val conversionList = mutableListOf<RangeMap>()
    private var currentConversionMap = RangeMap()

    val almanac: Almanac by lazy {
        if (seedsAsRange) {
            Almanac(seedRanges, conversionList)
        } else {
            Almanac(seeds, conversionList)
        }
    }

    fun parseNext(line: String) {
        if (line.isBlank()) return
        when {
            line.startsWith("seeds:") ->   parseSeeds(line)
            line.endsWith("map:") ->       parseSectionName(line)
            else ->                              parseSubRange(line)
        }
    }

    private fun parseSeeds(line: String) {
        if (seedsAsRange) {
            seedRanges = line.substringAfter("seeds: ").trim().split(" ").map { it.toLong() }
                .chunked(2).map { KeyRange(it[0], it[1]) }
        } else {
            seeds = line.substringAfter("seeds: ").trim().split(" ").map { it.toLong() }
        }
    }

    private fun parseSectionName(line: String) {
        currentConversionMap = RangeMap()
        conversionList.add(currentConversionMap)
    }

    private fun parseSubRange(line: String) {
        val nums = line.split(" ").map { it.toLong() }
        val subRange = MappedSubRange(nums[1], nums[0], nums[2])
        currentConversionMap.add(subRange)
    }
}