package com.tck.kotlintrain.juc.cache

/**
 *
 * @author tck88
 *
 * @date 2022-04-21
 */
class ExpensiveFunction : Computable<String, Int> {

    override fun compute(arg: String): Int {
        Thread.sleep(1000)
        return arg.toIntOrNull() ?: -1
    }
}