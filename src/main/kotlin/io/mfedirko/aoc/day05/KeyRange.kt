package io.mfedirko.aoc.day05

internal open class KeyRange(val startKey: Long, val length: Long) {
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