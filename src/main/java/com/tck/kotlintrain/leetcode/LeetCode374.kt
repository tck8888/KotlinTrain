package com.tck.kotlintrain.leetcode

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class LeetCode374 : GuessGame() {

    fun guessNumber(n: Int): Int {
        var left = 1
        var right = n
        while (left < right) {
            val mid = (right - left) shr 1 + left
            if (guess(mid) <= 0) {
                right = mid
            } else {
                left = mid + 1
            }
        }
        return left
    }
}

open class GuessGame {
    fun guess(num: Int): Int {
        return -1
    }
}

fun main() {
    println((8-3) shr 1)
}