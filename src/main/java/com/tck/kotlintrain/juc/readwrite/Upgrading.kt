package com.tck.kotlintrain.juc.readwrite

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *
 * @author tck88
 *
 * @date 2022-04-23
 */
class Upgrading {
    private val readWriteLock = ReentrantReadWriteLock(true)

    private val readLock = readWriteLock.readLock()
    private val writeLock = readWriteLock.writeLock()

    fun read() {
        println("${Thread.currentThread().name}开始尝试获取读锁")
        readLock.lock()
        try {
            println("${Thread.currentThread().name}得到了读锁，正在读取")
            Thread.sleep(20)
            println("升级会带来阻塞")
            writeLock.lock()
            println("${Thread.currentThread().name}得到了写锁，升级成功")
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
            readLock.lock()
            println("${Thread.currentThread().name}在不释放写锁的情况下，直接获取读锁，成功降级")
        } catch (e: InterruptedException) {
        } finally {
            readLock.unlock()
            println("${Thread.currentThread().name}释放了写锁")
            writeLock.unlock()
        }
    }
}

fun main() {
    val nonfairBargeDemo = Upgrading()
    // 降级是可以的
    Thread({ nonfairBargeDemo.read() }, "Thread-1").start()

}