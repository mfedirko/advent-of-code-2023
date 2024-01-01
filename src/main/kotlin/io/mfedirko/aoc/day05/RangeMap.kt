package io.mfedirko.aoc.day05

internal class RangeMap {
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