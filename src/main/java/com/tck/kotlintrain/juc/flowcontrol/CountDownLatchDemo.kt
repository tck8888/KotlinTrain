package com.tck.kotlintrain.juc.flowcontrol.countdownlatch

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 *
 * @author tck88
 *
 * @date 2022-04-27
 */
/**
 * 用法一
 * 一个线程等待多个线程都执行完毕，在继续自己的工作
 */
fun test1() {
    val countDown = 5
    val countDownLatch = CountDownLatch(countDown)
    val service = Executors.newFixedThreadPool(countDown)
    for (i in 1..countDown) {
        val runnable = Runnable {
            try {
                Thread.sleep((Math.random() * 10000L).toLong())
                println("No.$i 完成了检查")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                countDownLatch.countDown()
            }
        }
        service.submit(runnable)
    }

    println("等待$countDown 个人检查完。。。")

    countDownLatch.await()

    println("所有人都检查完。。。")

    service.shutdown()
}

/**
 * 多个线程等待某一个线程的信号，同时开始执行
 */
fun test2() {
    val countDown = 1
    val countDownLatch = CountDownLatch(countDown)
    val service = Executors.newFixedThreadPool(5)
    for (i in 1..5) {
        val runnable = Runnable {
            println("${i}准备完毕，等待发令枪")
            try {
                countDownLatch.await()
                println("${i}开始跑步")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        service.submit(runnable)
    }

    Thread.sleep(5000)
    println("发令枪响，比赛开始")
    countDownLatch.countDown()

    service.shutdown()
}

/**
 * 多个线程等多个线程完成执行后，在同时执行
 */
fun test3() {
    val begin = CountDownLatch(1)
    val end = CountDownLatch(5)
    val service = Executors.newFixedThreadPool(5)
    for (i in 1..5) {
        val runnable = Runnable {
            println("${i}准备完毕，等待发令枪")
            try {
                begin.await()
                println("${i}开始跑步")
                Thread.sleep((Math.random() * 10000L).toLong())
                println("${i}跑到终点了")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                end.countDown()
            }
        }
        service.submit(runnable)
    }

    Thread.sleep(5000)
    println("发令枪响，比赛开始")
    begin.countDown()
    println("------------")
    end.await()
    println("所有人都到达终点，比赛结束")

    service.shutdown()
}


fun main() {
    test3()
}