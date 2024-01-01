package io.mfedirko.aoc

import kotlin.properties.Delegates

object Day5 {

    fun partOne(): Long {
        with(InputParser()) {
            InputReaderUtil.read("day5")
                .forEach { parseNext(it) }
            return almanac.lowestLocation
        }
    }

    class InputParser {
        var currentSection: String by Delegates.notNull()

        var seeds: List<Long> by Delegates.notNull()
        val seedToSoil: RangeMap = RangeMap()
        val soilToFertilizer: RangeMap = RangeMap()
        val fertilizerToWater: RangeMap = RangeMap()
        val waterToLight: RangeMap = RangeMap()
        val lightToTemp: RangeMap = RangeMap()
        val tempToHumidity: RangeMap = RangeMap()
        val humidityToLocation: RangeMap = RangeMap()

        val almanac: Almanac by lazy {
            Almanac(seeds, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHumidity, humidityToLocation)
        }

        fun parseNext(line: String) {
            if (line.isBlank()) return
            when {
                line.startsWith("seeds:") ->   parseSeeds(line)
                line.endsWith("map:") ->       parseSectionName(line)
                else ->                              parseSubRange(line)
            }
        }

        fun parseSeeds(line: String) {
            seeds = line.substringAfter("seeds: ").trim().split(" ").map { it.toLong() }
        }

        fun parseSectionName(line: String) {
            currentSection = line.substringBefore(" map")
        }

        fun parseSubRange(line: String) {
            val nums = line.split(" ").map { it.toLong() }
            val subRange = SubRange(nums[1], nums[0], nums[2])
            when(currentSection) {
                "seed-to-soil" ->               seedToSoil.add(subRange)
                "soil-to-fertilizer" ->         soilToFertilizer.add(subRange)
                "fertilizer-to-water" ->        fertilizerToWater.add(subRange)
                "water-to-light" ->             waterToLight.add(subRange)
                "light-to-temperature" ->       lightToTemp.add(subRange)
                "temperature-to-humidity" ->    tempToHumidity.add(subRange)
                "humidity-to-location" ->       humidityToLocation.add(subRange)
                else -> throw java.lang.IllegalStateException("Invalid currentSection: $currentSection")
            }
        }

    }

    class Almanac(seeds: List<Long>,
                  seedToSoil: RangeMap,
                  soilToFertilizer: RangeMap,
                  fertilizerToWater: RangeMap,
                  waterToLight: RangeMap,
                  lightToTemp: RangeMap,
                  tempToHumidity: RangeMap,
                  humidityToLocation: RangeMap) {

        val lowestLocation: Long by lazy {
            seeds.asSequence().map { seedToSoil.get(it) }
                .map { soilToFertilizer.get(it) }
                .map { fertilizerToWater.get(it) }
                .map { waterToLight.get(it) }
                .map { lightToTemp.get(it) }
                .map { tempToHumidity.get(it) }
                .map { humidityToLocation.get(it) }
                .min()
        }
    }

    class RangeMap {
        private val _ranges: MutableList<SubRange> = mutableListOf()
        private val ranges: List<SubRange> by lazy {
            _ranges.sortedBy { it.startKey }
        }

        fun add(subRange: SubRange) {
            _ranges.add(subRange)
        }

        fun get(key: Long): Long {
            return ranges.binarySearch { subRange -> if (subRange.contains(key)) 0 else (subRange.startKey - key).toInt() }
                .takeIf { it >= 0 }?.let { i -> ranges[i].get(key) }
                ?: key
        }
    }

    class SubRange(val startKey: Long, val startValue: Long, val length: Long) {
        fun contains(key: Long): Boolean {
            return key >= startKey && key < startKey + length
        }

        fun get(key: Long): Long? {
            if (!contains(key)) return null
            return key - startKey + startValue
        }
    }
}

fun main(args: Array<String>) {
    println(Day5.partOne())
}