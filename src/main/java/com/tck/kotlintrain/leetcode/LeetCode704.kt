package com.tck.kotlintrain.leetcode

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class LeetCode704 {

    fun search(nums: IntArray, target: Int): Int {
        var low = 0
        var high = nums.size - 1
        while (low <= high) {
            val middle = (high - low) shr 1 + low
            println(middle)
            val num = nums[middle]
            if (num == target) {
                return middle
            } else if (num > target) {
                high = middle - 1
            } else {
                low = middle + 1
            }
        }
        return -1
    }
}

fun main() {
    // 4
    println(LeetCode704().search(intArrayOf(-1, 0, 3, 5, 9, 12), 9))
    //-1
    println(LeetCode704().search(intArrayOf(-1,0,3,5,9,12), 2))
}