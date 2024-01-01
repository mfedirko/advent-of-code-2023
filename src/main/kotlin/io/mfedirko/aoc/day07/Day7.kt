package io.mfedirko.aoc.day07

import io.mfedirko.aoc.InputReaderUtil

/**
 * https://adventofcode.com/2023/day/7
 */
object Day7 {

    fun partOne(input: Sequence<String>, jokerAsWildCard: Boolean = false): Int {
        return input
            .map { parseHandBid(it) }
            .sortedByDescending { Hand(it.first, jokerAsWildCard) }
            .mapIndexed { index, pair -> (index + 1) * pair.second }
            .sum()

    }

    fun partTwo(input: Sequence<String>): Int {
        return partOne(input, jokerAsWildCard = true)

    }

    private fun parseHandBid(line: String): Pair<String, Int> {
        val split = line.split(" ")
        return split[0] to split[1].toInt()
    }


    class Hand(private val value: String, private val jokerAsWildCard: Boolean): Comparable<Hand> {
        private val type: HandType by determineHandType()

        private fun determineHandType() = lazy {
            if (jokerAsWildCard) {
                val (maxCount, maxType) = maxCountWithJoker()
                with(countByCard(value, exclude = listOf('J', maxType)).values) {
                    when {
                        maxCount == 5 -> HandType.FIVE_OF_A_KIND

                        maxCount == 4 -> HandType.FOUR_OF_A_KIND

                        maxCount == 3 && any { it == 2 } -> HandType.FULL_HOUSE

                        maxCount == 3 && any { it == 1 } -> HandType.THREE_OF_A_KIND

                        maxCount == 2 && any { it == 2 } -> HandType.TWO_PAIR

                        maxCount == 2 -> HandType.ONE_PAIR

                        else -> HandType.HIGH_CARD
                    }
                }
            } else {
                with(countByCard(value).values) {
                    when {
                        any { it == 5 } -> HandType.FIVE_OF_A_KIND

                        any { it == 4 } -> HandType.FOUR_OF_A_KIND

                        any { it == 3 } && any { it == 2 } -> HandType.FULL_HOUSE

                        any { it == 3 } && any { it == 1 } -> HandType.THREE_OF_A_KIND

                        count { it == 2 } == 2 -> HandType.TWO_PAIR

                        count { it == 2 } == 1 && count { it == 1 } == 3 -> HandType.ONE_PAIR

                        else -> HandType.HIGH_CARD
                    }
                }
            }
        }

        private fun maxCountWithJoker(): Pair<Int, Char> {
            with(countByCard(value)) {
                val maxExcludingJoker = filter { it.key != 'J' }.maxByOrNull { it.value }
                val countJoker = this['J'] ?: 0
                val count = (maxExcludingJoker?.value ?: 0) + countJoker
                val key = maxExcludingJoker?.key ?: 'J'
                return count to key
            }
        }

        private val cardRanks = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A').reversed()
        private val cardRanksJokerLast = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A').reversed()

        override fun compareTo(other: Hand): Int {
            val byType = type.compareTo(other.type)
            return if (byType != 0) byType
                   else {
                       for (i in value.indices) {
                           val ranks = if (jokerAsWildCard) cardRanksJokerLast else cardRanks
                           val byCardRank = ranks.indexOf(value[i]) - ranks.indexOf(other.value[i])
                           if (byCardRank != 0) {
                               return byCardRank
                           }
                       }
                       return 0
                   }
        }

        companion object {
            fun countByCard(value: String, exclude: Collection<Char> = listOf()): Map<Char, Int> {
                return value.filter { !exclude.contains(it) }.groupingBy { it }.eachCount()
            }
        }
    }

    enum class HandType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD;
    }
}
