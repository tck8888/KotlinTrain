package com.tck.kotlintrain.juc.threadLocal

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.HashSet

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class ThreadLocalNormal {

    /**
     * 线程安全
     */
    fun date(seconds: Int): String {
        val date = Date(seconds * 1000L)
        return SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date)
    }

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    /**
     * 线程不安全
     */
    fun date1(seconds: Int): String {
        val date = Date(seconds * 1000L)
        return simpleDateFormat.format(date)
    }


    /**
     * 线程安全
     */
    @Synchronized
    fun date2(seconds: Int): String {
        val date = Date(seconds * 1000L)
        return simpleDateFormat.format(date)
    }

    /**
     * 线程安全
     */
    fun date3(seconds: Int): String {
        val date = Date(seconds * 1000L)
        return ThreadSafeFormatter.dateFormatThreadLocal.get().format(date)
    }
}

class ThreadSafeFormatter {
    companion object {
        val dateFormatThreadLocal = object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            }
        }
    }
}

fun main() {
//    Thread {
//        println(ThreadLocalNormal().date(10))
//    }.start()


//    for (i in 0..10) {
//        Thread {
//            println(ThreadLocalNormal().date(i))
//        }.start()
//    }

    val newFixedThreadPool = Executors.newFixedThreadPool(10)
    val threadLocalNormal = ThreadLocalNormal()
    val set = HashSet<String>()
    val set1 = HashSet<String>()
    val set2 = HashSet<String>()
    val set3 = HashSet<String>()
    for (i in 0..1000) {
        newFixedThreadPool.submit {
           // val date = threadLocalNormal.date(i)
            val date1 = threadLocalNormal.date1(i)
            val date2 = threadLocalNormal.date2(i)
           // val date3 = threadLocalNormal.date3(i)
//            set.add(date)
           set1.add(date1)
            set2.add(date2)
           // set3.add(date3)
        }
    }

    newFixedThreadPool.shutdown()
    Thread.sleep(5000)
    println(set.size)
    println(set1.size)
    println(set2.size)
    println(set3.size)

}