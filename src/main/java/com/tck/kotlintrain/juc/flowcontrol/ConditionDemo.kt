package com.tck.kotlintrain.juc.flowcontrol

import java.util.*
import java.util.concurrent.locks.ReentrantLock

/**
 *
 * @author tck88
 *
 * @date 2022-04-27
 */
class ConditionDemo {
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    fun method1() {
        lock.lock()
        try {
            println("条件不满足，开始await")
            condition.await()
            println("条件满足了，开始执行后续的任务")
        } finally {
            lock.unlock()
        }
    }

    fun method2() {
        lock.lock()
        try {
            println("准备工作完成，唤醒其他线程")
            condition.signal()
        } finally {
            lock.unlock()
        }
    }
}

fun test() {
    val conditionDemo = ConditionDemo()
    Thread {
        Thread.sleep(100)
        conditionDemo.method2()
    }.start()
    conditionDemo.method1()
}

class ConditionDemo2 {

    private val queueSize = 10
    private val queue = PriorityQueue<Int>(queueSize)
    private val lock = ReentrantLock()
    private val notFull = lock.newCondition()
    private val notEmpty = lock.newCondition()

    inner class Consumer : Thread() {
        override fun run() {
            super.run()
            consume()
        }

        private fun consume() {
            while (true) {
                lock.lock()
                try {
                    while (queue.size == 0) {
                        println("队列空，等待数据")
                        try {
                            notEmpty.await()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    queue.poll()
                    notFull.signalAll()
                    println("从队列取走了一个数据，队列剩余${queue.size}个元素")
                } finally {
                    lock.unlock()
                }
            }
        }
    }

    inner class Producer : Thread() {
        override fun run() {
            super.run()
            produce()
        }

        private fun produce() {
            while (true) {
                lock.lock()
                try {
                    while (queue.size == queueSize) {
                        println("队列满，等待有空余")
                        try {
                            notFull.await()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                    queue.offer(1)
                    notEmpty.signalAll()
                    println("从队列插入了了一个数据，队列剩余空间${queueSize - queue.size}")
                } finally {
                    lock.unlock()
                }
            }
        }
    }

}


fun main() {
    val conditionDemo2 = ConditionDemo2()
    val consumer = conditionDemo2.Consumer()
    val producer = conditionDemo2.Producer()
    consumer.start()
    producer.start()
}