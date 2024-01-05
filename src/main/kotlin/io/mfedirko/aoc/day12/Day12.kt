package io.mfedirko.aoc.day12

import io.mfedirko.aoc.Solution

object Day12: Solution<Long> {
    override fun partOne(input: Sequence<String>): Long {
        return solve(input.map { parsePartOne(it) })
    }

    override fun partTwo(input: Sequence<String>): Long {
        return solve(input.map { parsePartTwo(it) })
    }

    private fun solve(input: Sequence<Pair<String, List<Int>>>): Long {
        return input.map { pair -> countMatches(pair.second, pair.first, mutableMapOf()) }
            .fold(0L) { acc, next -> acc + next }
    }

    private fun parsePartOne(line: String): Pair<String, List<Int>> {
        val split = line.split(" ")
        return split[0] to split[1].split(",").map { it.toInt() }.toList()
    }

    private fun parsePartTwo(line: String): Pair<String, List<Int>> {
        val split = line.split(" ")
        return unfold(split[0], separator = "?") to
               unfold(split[1], separator = ",").split(",").map { it.toInt() }.toList()
    }

    private fun unfold(text: String, separator: String): String {
        return (0 until 5).joinToString(separator) { text }
    }

    private fun countMatches(nums: List<Int>, input: String, cache: MutableMap<String, Long>): Long {
        val key = "$input $nums"
        if (!cache.containsKey(key)) {
            cache[key] = doCountMatches(nums, input, cache)
        }
        return cache[key]!!
    }

    private fun doCountMatches(nums0: List<Int>, input0: String, cache: MutableMap<String, Long>): Long {
        var nums = nums0
        var input = input0
        while (nums.isNotEmpty() && input.isNotEmpty()) {
            val groupSize = nums[0]
            input = input.trimStart('.')
            when {
                input.isEmpty() -> return 0
                input[0] == '?' -> return sequenceOf('.', '#').sumOf {
                    countMatches(nums, "${it}${input.substring(1)}", cache)
                }
                input.length < groupSize -> return 0
                input.substring(0, groupSize).contains('.') -> return 0
                nums.size > 1 && (input.length < groupSize + 1 || input[groupSize] == '#') -> return 0
                nums.size > 1 -> {
                    input = input.substring(groupSize + 1)
                    nums = nums.subList(1, nums.size)
                }
                else -> {
                    input = input.substring(groupSize)
                    nums = nums.subList(1, nums.size)
                }
            }
        }
        return when {
            nums.isEmpty() && input.contains('#') -> 0
            nums.isEmpty() -> 1
            else -> 0
        }
    }

}
