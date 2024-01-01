package io.mfedirko.aoc

import java.math.BigInteger

object MathUtil {
    // least common multiple
    fun lcm(nums: Collection<Int>): Long {
        var result = nums.elementAt(0).toBigInteger()
        for (i in 1 until nums.size) {
            result = lcm(result, nums.elementAt(i).toBigInteger())
        }
        return result.toLong()
    }

    fun lcm(a: BigInteger, b: BigInteger): BigInteger {
        val larger = if (a > b) a else b
        val maxLcm = a * b
        var lcm = larger
        while (lcm <= maxLcm) {
            if (lcm % a == BigInteger.ZERO && lcm % b == BigInteger.ZERO) {
                return lcm
            }
            lcm += larger
        }
        return maxLcm
    }
}