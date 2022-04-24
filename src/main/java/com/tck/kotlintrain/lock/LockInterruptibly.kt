package com.tck.kotlintrain.lock

import java.util.concurrent.locks.ReentrantLock

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class LockInterruptibly : Runnable {
    private val lock =ReentrantLock()

    override fun run() {
        println("${Thread.currentThread().name}尝试获取锁")
        try {
            lock.lockInterruptibly()
            try {
                println("${Thread.currentThread().name}拿到了锁")
                Thread.sleep(5000)
            } catch (e:InterruptedException){
                println("${Thread.currentThread().name}睡眠期间被中断")
                e.printStackTrace()
            } finally {
                lock.unlock()
                println("${Thread.currentThread().name}释放了锁")
            }
        } catch (e: InterruptedException) {
            println("${Thread.currentThread().name}等锁期间被中断")
            e.printStackTrace()
        }
    }

}

fun main() {
    val lockInterruptibly = LockInterruptibly()
    val thread = Thread(lockInterruptibly)
    thread.start()
    Thread(lockInterruptibly).start()

    try {
        Thread.sleep(200)
    } catch (e: Exception) {
    }
    thread.interrupt()
}