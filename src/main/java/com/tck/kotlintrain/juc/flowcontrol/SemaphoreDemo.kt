package com.tck.kotlintrain.juc.flowcontrol

import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

/**
 *
 * @author tck88
 *
 * @date 2022-04-27
 */


val semaphore = Semaphore(3, true)

class Task(private val name:String) : Runnable {
    override fun run() {
        try {
            semaphore.acquire(3)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        println("${Thread.currentThread().name}-${name}-获取到许可证")
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        println("${Thread.currentThread().name}-${name}-释放了许可证")
        semaphore.release(3)
    }

}

fun test1() {
    val service = Executors.newFixedThreadPool(4)

    for (i in 1..6) {
        service.submit(Task("task-${i}"))
    }
    service.shutdown()
}

fun main() {
    test1()
}