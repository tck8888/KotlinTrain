package com.tck.kotlintrain.juc.cache

import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @author tck88
 *
 * @date 2022-04-21
 */
class MyCache3<A, V>(private val c: Computable<A, V>) : Computable<A, V> {
    private val cache = ConcurrentHashMap<A, V>()

    @Synchronized
    override fun compute(arg: A): V {
        println("进入缓存机制")
        val v = cache[arg]
        return if (v == null) {
            val compute = c.compute(arg)
            cache[arg] = compute
            compute
        } else {
            v
        }
    }
}

fun main() {
    val cache = MyCache3(ExpensiveFunction())

    for (i in 1..4) {
        Thread {
            try {
                val compute = if (i == 2) {
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