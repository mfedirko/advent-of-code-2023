package io.mfedirko.aoc.day08

import io.mfedirko.aoc.MathUtil.lcm
import io.mfedirko.aoc.Solution
import java.math.BigInteger

/**
 * https://adventofcode.com/2023/day/8
 */
object Day8 : Solution<Long> {
    override fun partOne(input: Sequence<String>): Long {
        return buildTree(input.toList())
            .let { (dirs, tree) -> tree.numSteps(dirs.asSequence()) }
    }

    override fun partTwo(input: Sequence<String>): Long {
        return buildTree(input.toList(), parallel = true)
            .let { (dirs, tree) -> tree.numSteps(dirs.asSequence()) }
    }

    fun buildTree(input: List<String>, parallel: Boolean = false): Pair<String, Tree> {
        val nodeRegex = "[A-Z\\d]+".toRegex()
        val steps = input[0]
        val tree = Tree(parallel)
        for (line in input.stream().skip(2).toList()) {
            val matches = nodeRegex.findAll(line)
            val key = matches.elementAt(0).value
            val left = matches.elementAt(1).value
            val right = matches.elementAt(2).value
            tree.add(key, left, right)
        }
        return steps to tree
    }


    class Tree(private val parallel: Boolean = false) {
        private val nodes = mutableMapOf<String, TreeNode>()

        fun add(from: String, left: String, right: String) {
            nodes[from] = TreeNode(from, { nodes[left]!! }, { nodes[right]!! })
        }

        fun numSteps(input: Sequence<Char>): Long {
            return if (parallel) {
                numStepsPartTwo(input)
            } else {
                numStepsPartOne(input)
            }
        }

        private fun numStepsPartOne(input: Sequence<Char>): Long {
            var node = nodes["AAA"]!!
            var count = 0L
            while (node.key != "ZZZ") {
                for (dir: Char in input) {
                    count++
                    node = node.traverse(dir)
                }
            }
            return count
        }

        private fun numStepsPartTwo(input: Sequence<Char>): Long {
            var count = 0
            val curNodes: MutableList<TreeNode> = nodes.values.filter { it.isStartNode }.toMutableList()
            val nextNodes = mutableListOf<TreeNode>()
            val stepsToEnd = MutableList(curNodes.size) { 0 }
            while (stepsToEnd.any { it == 0 }) {
                for (dir: Char in input) {
                    nextNodes.clear()
                    for (curNode in curNodes) {
                        val node = curNode.traverse(dir)
                        nextNodes.add(node)
                    }
                    count++
                    curNodes.clear()
                    curNodes.addAll(nextNodes)
                }
                for (i in curNodes.indices) {
                    if (curNodes[i].isEndNode && stepsToEnd[i] == 0) {
                        stepsToEnd[i] = count
                    }
                }
            }
            return lcm(stepsToEnd)
        }
    }

    class TreeNode(val key: String, val left: () -> TreeNode, val right: () -> TreeNode) {
        val isStartNode: Boolean = key.endsWith("A")
        val isEndNode: Boolean = key.endsWith("Z")

        fun traverse(dir: Char): TreeNode {
            return if (dir == 'R') right() else left()
        }
    }
}
