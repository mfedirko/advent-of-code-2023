package io.mfedirko.aoc.day05

import kotlin.properties.Delegates

/**
 * https://adventofcode.com/2023/day/5
 */
object Day5 {

    fun partOne(input: Sequence<String>, seedsAsRange: Boolean = false): Long {
        with(InputParser(seedsAsRange)) {
            input.forEach { parseNext(it) }
            return almanac.lowestLocation
        }
    }

    fun partTwo(input: Sequence<String>): Long {
        return partOne(input, seedsAsRange = true)
    }

    class InputParser(private val seedsAsRange: Boolean) {
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

    class Almanac(seeds: List<Long>, private val conversionList: List<RangeMap>) {
        private var seedsRange: Collection<KeyRange> by Delegates.notNull()

        constructor(seeds: Collection<KeyRange>, conversionList: List<RangeMap>)
                : this(emptyList<Long>(), conversionList) {
            seedsRange = seeds
        }

        val lowestLocation: Long by lazy {
            if (seeds.isNotEmpty()) {
                seeds.asSequence().map {
                    conversionList.fold(it) {
                            acc, rangeMap -> rangeMap.get(acc)
                    }
                }.min()
            } else {
                seedsRange.asSequence().flatMap {
                    conversionList.fold(listOf(it)) {
                        acc, rangeMap -> acc.flatMap {
                            keyRange -> rangeMap.get(keyRange)
                        }
                    }
                }.map { it.startKey }
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
