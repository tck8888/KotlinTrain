package com.tck.kotlintrain.juc.cache

import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock

/**
 *
 * @author tck88
 *  设置缓存有效期
 * @date 2022-04-21
 */
class MyCache7<A, V>(val c: Computable<A, V>) : Computable<A, V> {
    val cache = ConcurrentHashMap<A, Future<V>>()

    val exec: ScheduledExecutorService = Executors.newScheduledThreadPool(5)

    @Synchronized
    fun compute(arg: A, expire: Long): V {
        if (expire > 0) {
            exec.schedule({ expire(arg) }, expire, TimeUnit.MILLISECONDS)
        }
        return compute(arg)
    }

    @Synchronized
    fun computeRandomExpire(arg: A): V {
        val d = Math.random() * 10000

        return compute(arg, d.toLong())
    }


    @Synchronized
    fun expire(arg: A) {
        val f = cache[arg]
        if (f != null) {
            if (!f.isDone) {
                println("Future 任务被取消")
                f.cancel(true)
            }
            println("过期时间到，缓存失效")
            cache.remove(arg)
        }
    }

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

val cache = MyCache7(MayFail())
val co = CountDownLatch(1)
fun main() {
//
//    val newFixedThreadPool = Executors.newFixedThreadPool(10)
//
//    val start = System.currentTimeMillis()
//    for (i in 1..30) {
//        newFixedThreadPool.submit {
//            try {
//                println("${Thread.currentThread().name}开始等待")
//                co.await()
//                println("${Thread.currentThread().name}被放行")
//                println("${cache.compute("6")}")
//            } catch (e: Exception) {
//                println("$e")
//            }
//        }
//    }
//    Thread.sleep(5000)
//    co.countDown()
//    newFixedThreadPool.shutdown()
//    println("耗时：${System.currentTimeMillis() - start}")


}

class PauseableThreadPool(
    corePoolSize: Int,
    maximumPoolSize: Int,
    keepAliveTime: Long,
    unit: TimeUnit,
    workQueue: BlockingQueue<Runnable>
) : ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue) {


    private var isPaused = false
    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    override fun beforeExecute(t: Thread?, r: Runnable?) {
        super.beforeExecute(t, r)
        lock.lock()
        try {
            while (isPaused) {
                condition.await()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }

    fun resume() {
        lock.lock()
        try {
            isPaused = false
            condition.signalAll()
        } finally {
            lock.unlock()
        }
    }

    private fun pause() {
        lock.lock()
        try {
            isPaused = true
        } finally {
            lock.unlock()
        }


    }

}