package com.tck.kotlintrain.juc.threadLocal

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class ThreadLocalNPE {
    private val thread = ThreadLocal<Long>()

    fun get(): Long {
        return thread.get()
    }

    fun set() {
        thread.set(Thread.currentThread().id)
    }
}

fun main() {
    val threadLocalNPE = ThreadLocalNPE()
    threadLocalNPE.set()
    println(threadLocalNPE.get())
}