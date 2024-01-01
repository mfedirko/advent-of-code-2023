package io.mfedirko.aoc

import kotlin.properties.Delegates

object Day5 {

    fun partOne(seedsAsRange: Boolean = false): Long {
        with(InputParser(seedsAsRange)) {
            InputReaderUtil.read("day5")
                .forEach { parseNext(it) }
            return almanac.lowestLocation
        }
    }

    fun partTwo(): Long {
        return partOne(true)
    }

    class InputParser(private val seedsAsRange: Boolean) {
        var currentSection: String by Delegates.notNull()

        var seeds: List<Long> by Delegates.notNull()
        var seedRanges: List<KeyRange> by Delegates.notNull()

        val seedToSoil: RangeMap = RangeMap()
        val soilToFertilizer: RangeMap = RangeMap()
        val fertilizerToWater: RangeMap = RangeMap()
        val waterToLight: RangeMap = RangeMap()
        val lightToTemp: RangeMap = RangeMap()
        val tempToHumidity: RangeMap = RangeMap()
        val humidityToLocation: RangeMap = RangeMap()

        val almanac: Almanac by lazy {
            if (seedsAsRange) {
                Almanac(seedRanges, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHumidity, humidityToLocation)
            } else {
                Almanac(seeds, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHumidity, humidityToLocation)
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

        fun parseSeeds(line: String) {
            if (seedsAsRange) {
                seedRanges = line.substringAfter("seeds: ").trim().split(" ").map { it.toLong() }
                    .chunked(2).map { KeyRange(it[0], it[1]) }
            } else {
                seeds = line.substringAfter("seeds: ").trim().split(" ").map { it.toLong() }
            }
        }

        fun parseSectionName(line: String) {
            currentSection = line.substringBefore(" map")
        }

        fun parseSubRange(line: String) {
            val nums = line.split(" ").map { it.toLong() }
            val subRange = MappedSubRange(nums[1], nums[0], nums[2])
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
                  private val seedToSoil: RangeMap,
                  private val soilToFertilizer: RangeMap,
                  private val fertilizerToWater: RangeMap,
                  private val waterToLight: RangeMap,
                  private val lightToTemp: RangeMap,
                  private val tempToHumidity: RangeMap,
                  private val humidityToLocation: RangeMap) {

        private var seedsRange: Collection<KeyRange> by Delegates.notNull()

        constructor(seeds: Collection<KeyRange>,
                    seedToSoil: RangeMap,
                    soilToFertilizer: RangeMap,
                    fertilizerToWater: RangeMap,
                    waterToLight: RangeMap,
                    lightToTemp: RangeMap,
                    tempToHumidity: RangeMap,
                    humidityToLocation: RangeMap) : this(emptyList<Long>(), seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHumidity, humidityToLocation) {
            seedsRange = seeds
        }

        val lowestLocation: Long by lazy {
            if (seeds.isNotEmpty()) {
                seeds.asSequence().map { seedToSoil.get(it) }
                    .map { soilToFertilizer.get(it) }
                    .map { fertilizerToWater.get(it) }
                    .map { waterToLight.get(it) }
                    .map { lightToTemp.get(it) }
                    .map { tempToHumidity.get(it) }
                    .map { humidityToLocation.get(it) }
                    .min()
            } else {
                seedsRange.asSequence().flatMap { seedToSoil.get(it) }
                    .flatMap { soilToFertilizer.get(it) }
                    .flatMap { fertilizerToWater.get(it) }
                    .flatMap { waterToLight.get(it) }
                    .flatMap { lightToTemp.get(it) }
                    .flatMap { tempToHumidity.get(it) }
                    .flatMap { humidityToLocation.get(it) }
                    .map { it.startKey }
                    .min()
            }
        }
     }

    class RangeMap {
        private val _ranges: MutableList<MappedSubRange> = mutableListOf()
        private val ranges: List<MappedSubRange> by lazy {
            _ranges.sortedBy { it.startKey }
        }

        fun add(subRange: MappedSubRange) {
            _ranges.add(subRange)
        }

        fun get(key: Long): Long {
            return ranges.binarySearch { subRange -> if (subRange.contains(key)) 0 else (subRange.startKey - key).toInt() }
                .takeIf { it >= 0 }?.let { i -> ranges[i].get(key) }
                ?: key
        }

        fun get(key: KeyRange): Collection<KeyRange> {
            val keyMatches = ranges.filter { it.contains(key) }
            val nonMatches = key.removeAll(keyMatches)
            return keyMatches.map { it.get(key)!! }.union(nonMatches)
        }
    }

    class MappedSubRange(startKey: Long, val startValue: Long, length: Long) : KeyRange(startKey, length) {

        fun contains(key: Long): Boolean {
            return key >= startKey && key < startKey + length
        }

        fun get(key: Long): Long? {
            if (!contains(key)) return null
            return key - startKey + startValue
        }

        fun contains(key: KeyRange): Boolean {
            return key.startKey < this.startKey + length && key.startKey + key.length >= this.startKey
        }

        fun get(key: KeyRange): KeyRange? {
            if (!contains(key)) return null
            val start = key.startKey.coerceAtLeast(startKey)
            val end = key.endKey.coerceAtMost(endKey)
            val len = toLength(start, end)
            return KeyRange(get(start)!!, len)
        }
    }
    open class KeyRange(val startKey: Long, val length: Long) {
        val endKey: Long = startKey + length - 1

        fun removeAll(keyMatches: Collection<KeyRange>): Collection<KeyRange> {
            val gaps: MutableList<KeyRange> = mutableListOf()
            var curStart: Long
            var prevEnd = startKey
            for (match in keyMatches) {
                curStart = match.startKey
                if (prevEnd < curStart) {
                    gaps.add(KeyRange(prevEnd, toLength(prevEnd, curStart)))
                }
                prevEnd = match.endKey + 1
            }
            if (prevEnd < endKey) {
                gaps.add(KeyRange(prevEnd, toLength(prevEnd, endKey)))
            }
            return gaps
        }

        companion object {
            fun toLength(start: Long, endIncl: Long): Long {
                return endIncl - start + 1;
            }
        }
    }
}

fun main(args: Array<String>) {
    println(Day5.partTwo())
}