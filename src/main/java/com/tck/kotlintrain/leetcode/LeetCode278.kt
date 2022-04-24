package com.tck.kotlintrain.leetcode

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
open class VersionControl {
    fun isBadVersion(version: Int): Boolean {
        return false
    }
}

class LeetCode278 : VersionControl() {

    fun firstBadVersion(n: Int): Int {
        var low = 1
        var high = n
        while (low < high) {
            val mid = (high - low) / 2 + low
            if (isBadVersion(mid)) {
                high = mid
            } else {
                low = mid + 1
            }
        }
        return low
    }
}

