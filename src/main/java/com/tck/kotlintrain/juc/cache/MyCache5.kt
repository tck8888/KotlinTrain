package com.tck.kotlintrain.juc.cache

import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

/**
 *
 * @author tck88
 *  putIfAbsent 原子操作 解决重复问题
 * @date 2022-04-21
 */
class MyCache5<A, V>(private val c: Computable<A, V>) : Computable<A, V> {
    private val cache = ConcurrentHashMap<A, Future<V>>()

    @Synchronized
    override fun compute(arg: A): V {
        var f = cache[arg]
        if (f == null) {
            val call = Callable { c.compute(arg) }
            val futureTask = FutureTask(call)
            f = cache.putIfAbsent(arg, futureTask)
            if (f == null) {
                f = futureTask
                println("从futureTask调用了计算函数")
                futureTask.run()
            }
        }
        return f.get()
    }
}

fun main() {
    val cache = MyCache5(ExpensiveFunction())

    Thread {
        try {
            println("第 1 次计算结果：${cache.compute("666")}")
        } catch (e: Exception) {
            println("第 1 次计算 error:$e")
        }
    }.start()

    Thread {
        try {
            println("第 2 次计算结果：${cache.compute("666")}")
        } catch (e: Exception) {
            println("第 2 次计算 error:$e")
        }
    }.start()

    Thread {
        try {
            println("第 3 次计算结果：${cache.compute("667")}")
        } catch (e: Exception) {
            println("第 3 次计算 error:$e")
        }
    }.start()

}