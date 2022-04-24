package com.tck.kotlintrain.juc.reentrantlock

import java.util.concurrent.locks.ReentrantLock
import kotlin.random.Random

/**
 *
 * @author tck88 演示公平和不公平两种情况
 *
 * @date 2022-04-23
 */
class FairLock {

}

class PrintQueue {
    private val queueLock = ReentrantLock(false)

    fun printJob(document: Any) {
        queueLock.lock()
        try {
            val d = Random.nextLong(2) + 1
            println("${Thread.currentThread().name}:正在打印，需要${d}s")
            Thread.sleep(d * 1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            queueLock.unlock()
        }

        queueLock.lock()
        try {
            val d = Random.nextLong(2) + 1
            println("${Thread.currentThread().name}:正在打印，需要${d}s")
            Thread.sleep(d * 1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            queueLock.unlock()
        }
    }
}

class Job(private val job: PrintQueue) : Runnable {
    override fun run() {
        println("${Thread.currentThread().name}开始打印")
        job.printJob("")
        println("${Thread.currentThread().name}开始完成")
    }

}

fun main() {

    val threads = arrayListOf<Thread>()
    val printQueue = PrintQueue()
    for (i in 0..9) {
        threads.add(Thread(Job(printQueue)))
    }
    threads.forEach {
        it.start()
        Thread.sleep(100)
    }
}