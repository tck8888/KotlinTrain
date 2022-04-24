package com.tck.kotlintrain.juc.readwrite

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *
 * @author tck88
 * 演示公平 非公平的策略
 * @date 2022-04-23
 */
class NonfairBargeDemo {

    private val readWriteLock = ReentrantReadWriteLock(true)

    private val readLock = readWriteLock.readLock()
    private val writeLock = readWriteLock.writeLock()

    fun read() {
        println("${Thread.currentThread().name}开始尝试获取读锁")
        readLock.lock()
        try {
            println("${Thread.currentThread().name}得到了读锁，正在读取")
            Thread.sleep(20)
        } catch (e: InterruptedException) {
        } finally {
            println("${Thread.currentThread().name}释放了读锁")
            readLock.unlock()
        }
    }

    fun write() {
        println("${Thread.currentThread().name}开始尝试获取写锁")
        writeLock.lock()
        try {
            println("${Thread.currentThread().name}得到了写锁，正在写入")
            Thread.sleep(40)
        } catch (e: InterruptedException) {
        } finally {
            println("${Thread.currentThread().name}释放了写锁")
            writeLock.unlock()
        }
    }
}

fun main() {
    val nonfairBargeDemo = NonfairBargeDemo()
    Thread({ nonfairBargeDemo.write() }, "Thread-1").start()
    Thread({ nonfairBargeDemo.read() }, "Thread-2").start()
    Thread({ nonfairBargeDemo.read() }, "Thread-3").start()
    Thread({ nonfairBargeDemo.write() }, "Thread-4").start()
    Thread({ nonfairBargeDemo.read() }, "Thread-5").start()
    Thread {
        val threads = arrayListOf<Thread>()
        for (i in 0..10) {
            threads.add(Thread({ nonfairBargeDemo.read() }, "子线程创建的Thread-$i"))
        }
        threads.forEach {
            it.start()
        }
    }.start()
}