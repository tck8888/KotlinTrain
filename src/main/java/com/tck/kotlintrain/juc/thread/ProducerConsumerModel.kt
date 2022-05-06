package com.tck.kotlintrain.juc.thread

import java.util.*


/**
 *
 * @author tck88
 *
 * @date 2022-05-04
 */
object ProducerConsumerModel {

    class EventStorage {
        private val maxSize = 10
        private val storage = LinkedList<Date>()

        companion object {
            private val lock = Object()
        }

        fun put() {
            synchronized(lock) {
                while (storage.size == maxSize) {
                    try {
                        lock.wait()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                storage.add(Date())
                println("仓库里有了${storage.size}个产品")
                lock.notify()
            }
        }

        fun take() {
            synchronized(lock) {
                while (storage.size == 0) {
                    try {
                        lock.wait()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                println("拿到了${storage.poll()},现在仓库还剩下${storage.size}")
                lock.notify()
            }
        }

    }

    class Producer(private val storage: EventStorage) : Runnable {
        override fun run() {
            for (i in 0..99) {
                storage.put()
            }
        }
    }

    class Consumer(private val storage: EventStorage) : Runnable {
        override fun run() {
            for (i in 0..99) {
                storage.take()
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val eventStorage = EventStorage()
        val producer = Producer(eventStorage)
        val consumer = Consumer(eventStorage)
        Thread(producer).start()
        Thread(consumer).start()
    }
}