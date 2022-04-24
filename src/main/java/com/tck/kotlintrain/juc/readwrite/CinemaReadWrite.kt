package com.tck.kotlintrain.juc.readwrite

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 *
 * @author tck88
 *
 * @date 2022-04-23
 */
class CinemaReadWrite {

    private val readWriteLock = ReentrantReadWriteLock()

    private val readLock = readWriteLock.readLock()
    private val writeLock = readWriteLock.writeLock()

    fun read() {
        readLock.lock()
        try {
            println("${Thread.currentThread().name}得到了读锁，正在读取")
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
        } finally {
            println("${Thread.currentThread().name}释放了读锁")
            readLock.unlock()
        }
    }

    fun write() {
        writeLock.lock()
        try {
            println("${Thread.currentThread().name}得到了写锁，正在写入")
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
        } finally {
            println("${Thread.currentThread().name}释放了写锁")
            writeLock.unlock()
        }
    }
}

fun main() {
    val cinemaReadWrite = CinemaReadWrite()
    Thread { cinemaReadWrite.write() }.start()
    Thread { cinemaReadWrite.read() }.start()
    Thread { cinemaReadWrite.read() }.start()
    Thread { cinemaReadWrite.write() }.start()
    Thread { cinemaReadWrite.read() }.start()
}