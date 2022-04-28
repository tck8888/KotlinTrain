package com.tck.kotlintrain.juc.flowcontrol

import java.util.*
import java.util.concurrent.locks.AbstractQueuedSynchronizer

/**
 *
 * @author tck88
 *
 * @date 2022-04-28
 */
class OnShotLatch {

    private val sync = Sync()
    fun await() {
        sync.acquireShared(0)
    }

    fun signal() {
        sync.releaseShared(0)
    }

    private inner class Sync : AbstractQueuedSynchronizer() {
        override fun tryAcquireShared(arg: Int): Int {
            return if (state == 1) {
                1
            } else {
                -1
            }
        }

        override fun tryReleaseShared(arg: Int): Boolean {
            state = 1

            return true
        }
    }
}

fun main() {
    val onShotLatch = OnShotLatch()

    for (i in 1..2){
        Thread{
            println("${Thread.currentThread().name}:尝试获取Latch,获取失败就等待")
            onShotLatch.await()
            println("${Thread.currentThread().name}:开闸放行，继续运行")
        }.start()
    }
    Thread.sleep(5000)
    onShotLatch.signal()

    Thread({
        println("${Thread.currentThread().name}:尝试获取Latch,获取失败就等待")
        onShotLatch.await()
        println("${Thread.currentThread().name}:开闸放行，继续运行")
    },"Thread-3").start()

}