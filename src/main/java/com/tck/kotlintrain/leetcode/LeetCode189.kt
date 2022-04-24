package com.tck.kotlintrain.leetcode

/**
 *
 * @author tck88
 *  https://leetcode-cn.com/problems/rotate-array/
 * 给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 * @date 2022-04-22
 */
class LeetCode189 {

    fun rotate(nums: IntArray, k: Int) {
        var index = k
        val size = nums.size - 1
        while (index > 0) {
            val temp = nums[0]
            nums[0] = nums[size]
            for (i in size downTo 1) {
                if (i == 1) {
                    nums[i] = temp
                } else {
                    nums[i] = nums[i - 1]
                }
            }
            index--
        }
    }
}

fun main() {
    val leetCode189 = LeetCode189()
    // 5,6,7,1,2,3,4
    val intArrayOf1 = intArrayOf(1, 2, 3, 4, 5, 6, 7)
    leetCode189.rotate(intArrayOf1, 3)
    println(intArrayOf1.toList())
    //3,99,-1,-100
    val intArrayOf = intArrayOf(-1, -100, 3, 99)
    leetCode189.rotate(intArrayOf, 2)
    println(intArrayOf.toList())
}