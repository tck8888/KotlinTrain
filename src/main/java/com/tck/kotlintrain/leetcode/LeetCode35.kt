package com.tck.kotlintrain.leetcode

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class LeetCode35 {

    fun searchInsert(nums: IntArray, target: Int): Int {
        var low = 0
        var high = nums.size - 1
        var ans = nums.size
        while (low <= high) {
            val mid = (high - low) / 2 + low
            println(mid)
            val num = nums[mid]
            if (target <= num) {
                ans = mid
                high = mid - 1
            } else {
                low = mid + 1
            }
        }
        return ans
    }
}

fun main() {
    // 2
     println(LeetCode35().searchInsert(intArrayOf(1,3,5,6),5))
    //1
    println(LeetCode35().searchInsert(intArrayOf(1, 3, 5, 6), 2))
    //4
      println(LeetCode35().searchInsert(intArrayOf(1,3,5,6),7))
}