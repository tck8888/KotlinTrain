package com.tck.kotlintrain.juc.flowcontrol

import java.util.concurrent.CyclicBarrier

/**
 *
 * @author tck88
 *
 * @date 2022-04-27
 */
class CyclicBarrierDemo(private val id: Int, private val cyclicBarrier: CyclicBarrier) : Runnable {

    override fun run() {
        println("线程${id}现在前往集合地点")
        Thread.sleep((Math.random() * 10000).toLong())
        println("线程${id}到了集合地点，等待其他人到达")
        cyclicBarrier.await()
        println("线程${id}出发了")
    }
}

fun main() {

    val cyclicBarrier = CyclicBarrier(2) {
        println("这一波人到齐了，大家统一出发")
    }
    for (i in 1..4){
        Thread(CyclicBarrierDemo(i,cyclicBarrier)).start()
    }
}