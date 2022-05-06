package com.tck.kotlintrain.juc.thread

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue


/**
 *
 * @author tck88
 *
 * @date 2022-05-02
 */

fun a() {
    val runnable = Runnable {
        var num = 1
        while (!Thread.currentThread().isInterrupted && num < 10000) {
            if (num % 100 == 0) {
                println("$num 是100的倍数")
            }
            num++
        }
        println("任务结束了")
    }

    val thread = Thread(runnable)
    thread.start()
    Thread.sleep(1)
    thread.interrupt()
}

fun b() {
    val runnable = Runnable {
        var num = 0
        try {
            while (!Thread.currentThread().isInterrupted && num <= 300) {
                if (num % 100 == 0) {
                    println("$num 是100的倍数")
                }
                num++
            }
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    val thread = Thread(runnable)
    thread.start()
    Thread.sleep(500)
    thread.interrupt()
}

fun c() {
    val runnable = Runnable {
        var num = 0
        try {
            while (num <= 10000) {
                if (num % 100 == 0) {
                    println("$num 是100的倍数")
                }
                num++
                Thread.sleep(10)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    val thread = Thread(runnable)
    thread.start()
    Thread.sleep(5000)
    thread.interrupt()
}

fun d() {
    val runnable = Runnable {
        var num = 0

        while (!Thread.currentThread().isInterrupted && num <= 10000) {
            if (num % 100 == 0) {
                println("$num 是100的倍数")
            }
            num++
            try {
                Thread.sleep(10)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

    }

    val thread = Thread(runnable)
    thread.start()
    Thread.sleep(5000)
    thread.interrupt()
}

class Task : Runnable {

    override fun run() {
        while (true && !Thread.currentThread().isInterrupted) {
            println("go")

            try {
                throwInMethod()
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                //保存日志、停止程序
                println("保存日志")
                e.printStackTrace()
            }
        }
    }

    @Throws(InterruptedException::class)
    private fun throwInMethod() {
        Thread.sleep(2000)
    }
}

fun e() {
    val thread = Thread(Task())
    thread.start()
    Thread.sleep(1000)
    thread.interrupt()
}

class Task2 : Runnable {

    override fun run() {
        while (true) {
            if (Thread.currentThread().isInterrupted) {
                println("Interrupted，程序运行结束")
                break
            }
            reInterrupt()
        }
    }

    private fun reInterrupt() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            e.printStackTrace()
        }
    }
}

fun f() {
    val thread = Thread(Task2())
    thread.start()
    Thread.sleep(1000)
    thread.interrupt()
}

fun g() {
    val runnable = Runnable {
        //模拟指挥军队：一共有5个连队，每个连队10人，以连队为单位，发放武器弹药，叫到号的士兵前去领取
        for (i in 0..4) {
            println("连队" + i + "开始领取武器")
            for (j in 0..9) {
                println(j)
                try {
                    Thread.sleep(50)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            println("连队" + i + "已经领取完毕")
        }
    }
    val thread = Thread(runnable)
    thread.start()
    try {
        Thread.sleep(1000)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    thread.stop()

}

class Task3 : Runnable {
    @Volatile
    var canceled = false

    override fun run() {
        var num = 0
        try {
            while (num <= 100000 && !canceled) {
                if (num % 100 == 0) {
                    println("$num 是100的倍数。")
                }
                num++
                Thread.sleep(1)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}

fun h() {
    val task3 = Task3()
    val thread = Thread(task3)
    thread.start()
    Thread.sleep(5000)
    task3.canceled = true
}

class WrongWayVolatileCantStop{
    class Producer(private val storage: BlockingQueue<Int>) : Runnable {
        @Volatile
        var canceled = false
        override fun run() {
            var num = 0
            try {
                while (num <= 100000 && !canceled) {
                    if (num % 100 == 0) {
                        storage.put(num)
                        println("$num 是100的倍数,被放到仓库中了。")
                    }
                    num++
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                println("生产者结束运行")
            }
        }
    }

     class Consumer(var storage: BlockingQueue<Int>) {
        fun needMoreNums(): Boolean {
            return Math.random() <= 0.95
        }
    }
}



fun i() {
    val storage: ArrayBlockingQueue<Int> = ArrayBlockingQueue<Int>(10)

    val producer = WrongWayVolatileCantStop.Producer(storage)
    val producerThread = Thread(producer)
    producerThread.start()
    Thread.sleep(1000)

    val consumer = WrongWayVolatileCantStop.Consumer(storage)
    while (consumer.needMoreNums()) {
        println("${consumer.storage.take()}被消费了")
        Thread.sleep(100)
    }
    println("消费者不需要更多数据了。")
    //一旦消费不需要更多数据了，我们应该让生产者也停下来，但是实际情况
    producer.canceled = true
    println(producer.canceled)
}

class WrongWayVolatileFixed{
    class Producer(private val storage: BlockingQueue<Int>) : Runnable {

        override fun run() {
            var num = 0
            try {
                while (num <= 100000 && !Thread.currentThread().isInterrupted) {
                    if (num % 100 == 0) {
                        storage.put(num)
                        println("$num 是100的倍数,被放到仓库中了。")
                    }
                    num++
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                println("生产者结束运行")
            }
        }
    }

    class Consumer(var storage: BlockingQueue<Int>) {
        fun needMoreNums(): Boolean {
            return Math.random() <= 0.95
        }
    }
}

fun j(){
    val storage: ArrayBlockingQueue<Int> = ArrayBlockingQueue<Int>(10)

    val producer = WrongWayVolatileFixed.Producer(storage)
    val producerThread = Thread(producer)
    producerThread.start()
    Thread.sleep(1000)

    val consumer = WrongWayVolatileFixed.Consumer(storage)
    while (consumer.needMoreNums()) {
        println("${consumer.storage.take()}被消费了")
        Thread.sleep(100)
    }
    println("消费者不需要更多数据了。")
    producerThread.interrupt()
}

fun main() {
    j()
}