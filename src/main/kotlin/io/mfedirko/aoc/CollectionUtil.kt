package io.mfedirko.aoc


object CollectionUtil {
    /*
     * T is expected to implement equals/hashcode as well as compareTo
     */
    fun <T : Comparable<T>> allCombinations(items: Collection<T>): Set<Pair<T, T>> {
        return items.flatMap { first ->
            items.filter { second -> second != first}
                .map {second ->
                    if (first < second) first to second else second to first
                }
        }.toSet()
    }
}