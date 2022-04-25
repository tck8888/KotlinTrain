package com.tck.kotlintrain.juc.atomic

import java.util.concurrent.atomic.AtomicInteger

/**
 *
 * @author tck88
 *
 * @date 2022-04-24
 */
class AtomicIntegerDemo1 : Runnable {

    companion object {
        val atomicInteger = AtomicInteger()

        @Volatile
        var basicCount = 0
    }

    fun incrementAtomic() {
        atomicInteger.getAndIncrement()
    }

    fun incrementBasic() {
        basicCount++
    }


    override fun run() {
        for (i in 1..10000) {
            incrementAtomic()
            incrementBasic()
        }
    }
}

fun main() {
    val atomicIntegerDemo1 = AtomicIntegerDemo1()
    val thread = Thread(atomicIntegerDemo1)
    val thread1 = Thread(atomicIntegerDemo1)
    thread.start()
    thread1.start()

    thread.join()
    thread1.join()

    println("basicCount:${AtomicIntegerDemo1.basicCount}")
    println("atomicInteger:${AtomicIntegerDemo1.atomicInteger.get()}")

}
