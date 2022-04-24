package com.tck.kotlintrain.test

import okhttp3.internal.wait
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors

/**
 *
 * @author tck88
 *
 * @date 2021/10/21
 */
object Q1 {
    val TAG = javaClass.simpleName
    val queue = ArrayBlockingQueue<String>(11)

    fun produce(msg: String) {
        queue.add(msg)
        println("$TAG produce msg  current size:${queue.size}")
    }

    fun consume(): String {
        try {
            return queue.take()
        } catch (e: Exception) {
            println("$TAG consume msg error:$e")
        }
        return ""
    }
}

object Q2 {
    val TAG = javaClass.simpleName
    val queue = ArrayBlockingQueue<String>(1)

    fun produce(msg: String) {
        queue.add(msg)
        println("$TAG produce msg  current size:${queue.size}")
    }

    fun consume(): String {
        try {
            val result = queue.take()
            return result
        } catch (e: Exception) {
            println("$TAG consume msg error:$e")
        }
        return ""
    }
}

object Q3 {
    val TAG = javaClass.simpleName
    val queue = ArrayBlockingQueue<String>(11)

    fun produce(msg: String) {
        queue.add(msg)
        println("$TAG produce msg  current size:${queue.size}")
    }

    fun consume(): String {
        try {
            val result = queue.take()
            println("$TAG consume msg $result current size:${queue.size}")
            return result
        } catch (e: Exception) {
            println("$TAG consume msg error:$e")
        }
        return ""
    }
}

class ConsumeRunnable1 : Runnable {
    val tag = javaClass.simpleName
    override fun run() {
        for (index in 0..10) {
            Q1.produce("index=$index")
        }
    }
}

class ConsumeRunnable2 : Runnable {
    val tag = javaClass.simpleName
    override fun run() {
        while (true) {
            try {
                val consume1 = Q1.consume()
                Thread.sleep(1000)
                Q2.produce(consume1)
            } catch (e: Exception) {
                println("$tag  error:$e")
            }
        }
    }
}

class ConsumeRunnable3 : Runnable {
    val tag = javaClass.simpleName
    override fun run() {
        while (true) {
            val consume1 = Q2.consume() ?: ""
            Q3.produce(consume1)
            Thread.sleep(1000)
            println("Q3.queue.size=${Q3.queue.size}")
        }
    }
}

fun main() {
    for (index in 0..10) {
        Q1.produce("index=$index")
    }

    val newFixedThreadPool = Executors.newFixedThreadPool(3)
   // newFixedThreadPool.submit( ConsumeRunnable1())
    newFixedThreadPool.submit( ConsumeRunnable2())
    newFixedThreadPool.submit( ConsumeRunnable3())


}