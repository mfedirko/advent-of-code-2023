package io.mfedirko.aoc.day12

import io.mfedirko.aoc.Solution

object Day12: Solution<Long> {
    override fun partOne(input: Sequence<String>): Long {
        return input.map { line ->
            val split = line.split(" ")
            split[0] to split[1].split(",").map { it.toInt() }.toList()
        }.map { pair -> countMatches(pair.second, pair.first) }
        .fold(0L) { acc, next -> acc + next }
    }

    override fun partTwo(input: Sequence<String>): Long {
        TODO("Not yet implemented")
    }

    fun countMatches(nums0: List<Int>, input0: String): Int {
        var nums = nums0
        var input = input0
        while (nums.isNotEmpty() && input.isNotEmpty()) {
            val groupSize = nums[0]
            input = input.trimStart('.')
            when {
                input.isEmpty() -> return 0
                input[0] == '?' -> return sequenceOf('.', '#').sumOf { countMatches(nums, "${it}${input.substring(1)}") }
                input.length < groupSize -> return 0
                input.substring(0, groupSize).contains('.') -> return 0
                nums.size > 1 && (input.length  < groupSize + 1 || input[groupSize] == '#') -> return 0
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
            else ->  0
        }
    }

}
