package io.mfedirko.aoc.day05

import kotlin.properties.Delegates

internal class Almanac(seeds: List<Long>, private val conversionList: List<RangeMap>) {
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