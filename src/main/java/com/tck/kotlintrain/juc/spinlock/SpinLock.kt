package com.tck.kotlintrain.juc.spinlock

import java.util.concurrent.atomic.AtomicReference

/**
 *
 * @author tck88
 *
 * @date 2022-04-23
 */
class SpinLock {

    private val sign = AtomicReference<Thread>()

    fun lock() {
        val currentThread = Thread.currentThread()
        while (!sign.compareAndSet(null, currentThread)) {
            //println("自旋获取失败，再次尝试")
        }
    }

    fun unLock() {
        val currentThread = Thread.currentThread()
        sign.compareAndSet(currentThread, null)
    }
}

fun main() {

    val spinLock = SpinLock()
    val runnable = Runnable {
        println("${Thread.currentThread().name}开始尝试获取自旋锁")
        spinLock.lock()
        println("${Thread.currentThread().name}获取到了自旋锁")
        try {
            Thread.sleep(300)
        } catch (e: InterruptedException) {
        } finally {
            println("${Thread.currentThread().name}释放了自旋锁")
            spinLock.unLock()
        }
    }
    Thread(runnable).start()
    Thread(runnable).start()
}