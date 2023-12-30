package io.mfedirko.aoc

object InputReaderUtil {
    fun read(name: String): Sequence<String> {
        return InputReaderUtil::class.java.getResourceAsStream("/inputs/${name}.txt")
            ?.bufferedReader()?.readLines()?.asSequence()!!
    }
}