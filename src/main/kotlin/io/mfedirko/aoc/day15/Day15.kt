package io.mfedirko.aoc.day15

import io.mfedirko.aoc.Solution

object Day15: Solution<Int> {
    override fun partOne(input: Sequence<String>): Int {
        return parse(input)
            .sumOf { hash(it) }
    }

    override fun partTwo(input: Sequence<String>): Int {
        val map = Hashmap()
        parsePart2(input).forEach { cmd ->
            when(cmd) {
                is Add -> map.add(cmd.lens)
                is Remove -> map.remove(cmd.label)
            }
        }
        return map.totalFocusingPower()
    }

    private fun hash(str: String): Int {
        return str.chars().reduce(0) { acc, next ->
            17 * (acc + next) % 256
        }
    }


    private fun parse(input: Sequence<String>) = input.first().trim('\n').split(",")

    private fun parsePart2(input: Sequence<String>): List<Command> {
        return parse(input).map {
            if (it.last() == '-') {
                val label = it.substring(0 until it.length - 1)
                Remove(label)
            } else {
                val split = it.split("=")
                val (label, focalLen) = split
                val lens = Lens(label, focalLen.toInt())
                Add(lens)
            }
        }
    }

    internal class Hashmap {
        private val boxes: Array<MutableList<Lens>> = Array(256) { mutableListOf() }

        fun add(lens: Lens) {
            val hash = hash(lens.label)
            boxes[hash].indices.firstOrNull { i -> boxes[hash][i].label == lens.label }
                ?.let { i -> boxes[hash][i] = lens }
            ?: boxes[hash].add(lens)
        }

        fun remove(label: String) {
            boxes[hash(label)].removeIf { it.label == label }
        }

        fun totalFocusingPower(): Int {
            return boxes.indices.sumOf { i ->
                (i + 1) * boxes[i].indices.sumOf { j ->
                    (j + 1) * boxes[i][j].focalLength
                }
            }
        }
    }

    internal sealed class Command {
    }

    internal class Add(val lens: Lens) : Command() {
    }

    internal class Remove(val label: String) : Command() {
    }

    internal data class Lens(val label: String, val focalLength: Int) {
    }
}