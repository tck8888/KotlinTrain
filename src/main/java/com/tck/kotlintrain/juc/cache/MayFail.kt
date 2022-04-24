package com.tck.kotlintrain.juc.cache

import java.io.IOException

/**
 *
 * @author tck88
 *
 * @date 2022-04-21
 */
class MayFail : Computable<String, Int> {
    override fun compute(arg: String): Int {
        val random = Math.random()
        if (random > 0.5) {
            throw  IOException("计算失败")
        }
        Thread.sleep(1500)
        return arg.toIntOrNull() ?: -1
    }
}