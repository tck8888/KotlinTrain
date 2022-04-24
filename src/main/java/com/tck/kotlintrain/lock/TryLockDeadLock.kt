package com.tck.kotlintrain.lock

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock
import kotlin.random.Random

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class TryLockDeadLock : Runnable {
    companion object {
        private val lock1 = ReentrantLock()
        private val lock2 = ReentrantLock()
    }

    var flag = 1

    override fun run() {
        for (i in 0..100) {
            if (flag == 1) {
                try {
                    if (lock1.tryLock(800, TimeUnit.MILLISECONDS)) {
                        try {
                            println("线程1 获取到了 lock1 ")
                            Thread.sleep(Random.nextLong(1000L))
                            if (lock2.tryLock(800, TimeUnit.MILLISECONDS)) {
                                try {
                                    println("线程1 获取到了 lock2 ")
                                    println("线程1 获取到了 两把锁 ")
                                    break
                                } finally {
                                    lock2.unlock()
                                }
                            } else {
                                println("线程1 获取lock2失败 以重试")
                            }
                        } finally {
                            lock1.unlock()
                            Thread.sleep(Random.nextLong(1000L))
                        }
                    } else {
                        println("线程1 获取lock1失败 以重试")
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            if (flag == 0) {
                try {
                    if (lock2.tryLock(3000, TimeUnit.MILLISECONDS)) {
                        try {
                            println("线程2 获取到了 lock2 ")
                            Thread.sleep(Random.nextLong(1000L))
                            if (lock1.tryLock(3000, TimeUnit.MILLISECONDS)) {
                                try {
                                    println("线程2 获取到了 lock1 ")
                                    println("线程2 获取到了 两把锁 ")
                                    break
                                } finally {
                                    lock1.unlock()
                                }
                            } else {
                                println("线程2 获取lock1失败 以重试")
                            }
                        } finally {
                            lock2.unlock()
                            Thread.sleep(Random.nextLong(1000L))
                        }
                    } else {
                        println("线程2 获取lock2失败 以重试")
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

}

fun main() {
    val tryLockDeadLock1 = TryLockDeadLock()
    tryLockDeadLock1.flag = 0
    val tryLockDeadLock2 = TryLockDeadLock()
    tryLockDeadLock2.flag = 1

    Thread(tryLockDeadLock1).start()

    Thread(tryLockDeadLock2).start()
}