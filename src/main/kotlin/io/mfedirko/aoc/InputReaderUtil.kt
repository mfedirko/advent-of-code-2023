package io.mfedirko.aoc

object InputReaderUtil {
    fun read(name: String): Sequence<String> {
        val fileName = "/inputs/${name}.txt"
        return InputReaderUtil::class.java.getResourceAsStream(fileName)
            ?.bufferedReader()?.readLines()?.asSequence()
            ?: throw java.lang.IllegalArgumentException("File not found: $fileName")
    }
}