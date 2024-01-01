package io.mfedirko.aoc

object Day7 {

    fun partOne(jokerAsWildCard: Boolean = false): Int {
        return InputReaderUtil.read("day7")
            .map { parseHandBid(it) }
            .sortedByDescending { Hand(it.first, jokerAsWildCard) }
            .mapIndexed { index, pair -> (index + 1) * pair.second }
            .sum()

    }

    fun partTwo(): Int {
        return partOne(true)

    }

    private fun parseHandBid(line: String): Pair<String, Int> {
        val split = line.split(" ")
        return split[0] to split[1].toInt()
    }


    class Hand(private val value: String, private val jokerAsWildCard: Boolean): Comparable<Hand> {
        private val type: CardType by lazy {
            if (jokerAsWildCard) {
                val (maxCount, maxType) = maxCountWithJoker()
                with(countByCard(value, exclude = listOf('J', maxType)).values) {
                    when {
                        maxCount == 5 -> CardType.FIVE_OF_A_KIND

                        maxCount == 4 -> CardType.FOUR_OF_A_KIND

                        maxCount == 3 && any { it == 2 } -> CardType.FULL_HOUSE

                        maxCount == 3 && any { it == 1 } -> CardType.THREE_OF_A_KIND

                        maxCount == 2 && any { it == 2 } -> CardType.TWO_PAIR

                        maxCount == 2 -> CardType.ONE_PAIR

                        else -> CardType.HIGH_CARD
                    }
                }
            } else {
                with(countByCard(value).values) {
                    when {
                        any { it == 5 } -> CardType.FIVE_OF_A_KIND

                        any { it == 4 } -> CardType.FOUR_OF_A_KIND

                        any { it == 3 } && any { it == 2 } -> CardType.FULL_HOUSE

                        any { it == 3 } && any { it == 1 } -> CardType.THREE_OF_A_KIND

                        count { it == 2 } == 2 -> CardType.TWO_PAIR

                        count { it == 2 } == 1 && count { it == 1 } == 3 -> CardType.ONE_PAIR

                        else -> CardType.HIGH_CARD
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

    enum class CardType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD;
    }
}

fun main(args: Array<String>) {
    println(Day7.partTwo())

}