package com.tck.kotlintrain.juc.cas

import java.util.concurrent.atomic.AtomicInteger

/**
 *
 * @author tck88
 *
 * @date 2022-04-25
 */
class SimulatedCAS {

    @Volatile
    private var a = 0


    @Synchronized
    fun compareAndSwap(expect: Int, update: Int):Int {
        val oldValue = a
        if (oldValue == expect) {
            a = update
        }
        return oldValue
    }
}