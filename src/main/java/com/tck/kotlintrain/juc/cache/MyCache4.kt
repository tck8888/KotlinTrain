package com.tck.kotlintrain.juc.cache

import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Future
import java.util.concurrent.FutureTask

/**
 *
 * @author tck88
 * 利用future避免重复计算
 * @date 2022-04-21
 */
class MyCache4<A, V>(private val c: Computable<A, V>) : Computable<A, V> {
    private val cache = ConcurrentHashMap<A, Future<V>>()

    @Synchronized
    override fun compute(arg: A): V {
        var f = cache[arg]
        if (f == null) {
            val call = Callable { c.compute(arg) }
            val futureTask = FutureTask(call)
            f = futureTask
            cache[arg] = futureTask
            println("从futureTask调用了计算函数")
            futureTask.run()
        }
        return f.get()
    }
}

fun main() {
    val cache = MyCache4(ExpensiveFunction())

    for (i in 1..4) {
        Thread {
            try {
                val compute = if (i == 4) {
                    cache.compute("667")
                } else {
                    cache.compute("666")
                }
                println("第 $i 次计算结果：$compute")
            } catch (e: Exception) {
                println("第 $i 次计算 error:$e")
            }
        }.start()
    }

}