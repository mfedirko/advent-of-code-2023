package io.mfedirko.aoc.day05

internal class MappedSubRange(startKey: Long, val startValue: Long, length: Long) : KeyRange(startKey, length) {

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