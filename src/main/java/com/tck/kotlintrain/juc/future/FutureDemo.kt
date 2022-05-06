package com.tck.kotlintrain.juc.future

import java.util.concurrent.*
import kotlin.random.Random

/**
 *
 * @author tck88
 *
 * @date 2022-04-29
 */

fun test1() {
    val service = Executors.newFixedThreadPool(5)
    val task = Callable {
        Thread.sleep(1000)
        Random.nextInt()
    }
    val future = service.submit(task)
    println(future.get())
    service.shutdown()
}

fun test2() {
    val service = Executors.newFixedThreadPool(2)
    val task = Callable {
        Thread.sleep(1000)
        Random.nextInt()
    }
    val list = ArrayList<Future<Int>>()
    for (i in 0..5) {
        list.add(service.submit(task))
    }
    list.forEach {
        println(it.get())
    }

    service.shutdown()
}

fun test3() {
    val service = Executors.newFixedThreadPool(20)
    val task = Callable<Int> {
        throw IllegalArgumentException("Callable抛出异常")
    }
    val future = service.submit(task)
    try {
        future.get()
    } catch (e: InterruptedException) {
        println("InterruptedException异常")
    } catch (e: ExecutionException) {
        println("ExecutionException异常")
    } catch (e: IllegalArgumentException) {
        println("IllegalArgumentException异常")
    }

    service.shutdown()
}

fun test4() {
    val service = Executors.newFixedThreadPool(20)
    val task = Callable<Int> {
        throw IllegalArgumentException("Callable抛出异常")
    }
    val future = service.submit(task)
    try {
        for (i in 0..5) {
            println(i)
            Thread.sleep(500)
        }
        println(future.isDone)
        future.get()
    } catch (e: InterruptedException) {
        println("InterruptedException异常")
    } catch (e: ExecutionException) {
        println("ExecutionException异常")
    } catch (e: IllegalArgumentException) {
        println("IllegalArgumentException异常")
    }
    service.shutdown()
}

data class Ad(val name: String)

val defaultAD = Ad("无网络时候的默认广告")

fun test5() {
    val service = Executors.newFixedThreadPool(10)
    val task = Callable {

        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            println("sleep期间被中断了")
            Ad("被中断时候的默认广告")
            return@Callable
        }
        println("正常执行")
        Ad("正常广告")
    }
    val future = service.submit(task)
    val ad = try {
        future.get(2000, TimeUnit.MILLISECONDS)
    } catch (e: InterruptedException) {
        Ad("InterruptedException广告")
    } catch (e: ExecutionException) {
        Ad("ExecutionException广告")
    } catch (e: TimeoutException) {
        println("cancel结果${future.cancel(true)}")
        Ad("TimeoutException广告")
    }
    service.shutdown()
    println(ad)
}

fun main() {
    test5()
}