package com.tck.kotlintrain.leetcode

/**
 *
 * @author tck88
 *https://leetcode-cn.com/problems/squares-of-a-sorted-array/
 * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
 *
 * @date 2022-04-23
 */
class LeetCode977 {
    fun sortedSquares(nums: IntArray): IntArray {
        var left = 0
        var right = nums.size - 1
        var pos = right
        val ans = IntArray(nums.size)
        while (left <= right) {
            val ll = nums[left] * nums[left]
            val rr = nums[right] * nums[right]
            if (ll < rr) {
                ans[pos] = rr
                right--
            } else {
                ans[pos] = ll
                left++
            }
            pos--
        }
        return ans
    }
}

fun main() {
    println(Math.pow(3.0, 2.0))

    // 0,1,9,16,100
   println( LeetCode977().sortedSquares(intArrayOf(-4,-1,0,3,10)).toList())
    // 4,9,9,49,121
 //  println( LeetCode977().sortedSquares(intArrayOf(-7,-3,2,3,11)).toList())
}