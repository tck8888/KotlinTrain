package com.tck.kotlintrain.juc.cache

import java.util.concurrent.*

/**
 *
 * @author tck88
 *  putIfAbsent 原子操作 解决重复问题
 * @date 2022-04-21
 */
class MyCache6<A, V>(val c: Computable<A, V>) : Computable<A, V> {
    val cache = ConcurrentHashMap<A, Future<V>>()

    @Synchronized
    override fun compute(arg: A): V {
        while (true) {
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
            try {
                return f.get()
            } catch (e: CancellationException) {
                //缓存污染
                cache.remove(arg)
                println("被取消")
                throw e
            } catch (e: InterruptedException) {
                cache.remove(arg)
                throw e
            } catch (e: ExecutionException) {
                cache.remove(arg)
                println("计算错误，需要重试")
            }
        }
    }
}

fun main() {
    val cache = MyCache6(MayFail())

    for (i in 1..9) {
        Thread {
            try {
                val compute = cache.compute("666")
                println("第 $i 次计算结果：$compute")
            } catch (e: Exception) {
                println("第 $i 次计算 error:$e")
            }
        }.start()
    }


}