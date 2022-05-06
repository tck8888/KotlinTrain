package com.tck.kotlintrain.juc.thread

/**
 *
 * @author tck88
 *两个线程交替打印0~100的奇偶数，用synchronized关键字实现
 * @date 2022-05-04
 */
object WaitNotifyPrintOddEvenSyn {
    private var count = 0
    private val lock = Object()

    //新建2个线程
    //1个只处理偶数，第二个只处理奇数（用位运算）
    //用synchronized来通信
    @JvmStatic
    fun main(args: Array<String>) {
        Thread({
            while (count < 100) {
                synchronized(lock) {
                    if (count and 1 == 0) {
                        println("${Thread.currentThread().name}:${count++}")
                    }
                }
            }
        }, "偶数").start()
        Thread({
            while (count < 100) {
                synchronized(lock) {
                    if (count and 1 == 1) {
                        println("${Thread.currentThread().name}:${count++}")
                    }
                }
            }
        }, "奇数").start()
    }
}


//
//两个线程交替打印0~100的奇偶数，用wait和notify
//
object WaitNotifyPrintOddEveWait {
    private var count = 0
    private val lock = Object()

    @JvmStatic
    fun main(args: Array<String>) {
        Thread(TurningRunner(), "偶数").start()
        Thread(TurningRunner(), "奇数").start()
    }

    //1. 拿到锁，我们就打印
    //2. 打印完，唤醒其他线程，自己就休眠
    internal class TurningRunner : Runnable {
        override fun run() {
            while (count <= 100) {
                synchronized(lock) {
                    //拿到锁就打印
                    println("${Thread.currentThread().name}:${count++}")
                    lock.notifyAll()
                    if (count <= 100) {
                        try {
                            //如果任务还没结束，就让出当前的锁，并休眠
                            lock.wait()
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }
}


object AlternatePrintABC {

    private var count = 0
    private val lock1 = Object()
    private val lock2 = Object()
    private val lock3 = Object()

    class MultiTurningRunner(
        private val nextLock: Object,
        private val currentLock: Object
    ) : Runnable {
        override fun run() {
            while (count <= 100) {
                synchronized(nextLock) {
                    synchronized(currentLock) {
                        println("${Thread.currentThread().name}:${count++}")
                        // 唤醒等待当前锁的线程
                        currentLock.notifyAll()
                    }
                    try {
                        // 如果还需要继续执行，则让出下一个线程对应的锁并进入等待状态
                        if (count <= 100) {
                            nextLock.wait()
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val t1 = Thread(MultiTurningRunner(lock2, lock1))
        val t2 = Thread(MultiTurningRunner(lock3, lock2))
        val t3 = Thread(MultiTurningRunner(lock1, lock3))
        t1.start()
        t2.start()
        t3.start()
    }
}