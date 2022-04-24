package com.tck.kotlintrain.juc.cache

/**
 *
 * @author tck88
 *
 * @date 2022-04-21
 */
class MyCache2<A, V>(private val c: Computable<A, V>) : Computable<A, V> {
    private val cache = HashMap<A, V>()

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
    val cache = MyCache2(ExpensiveFunction())
    val compute = cache.compute("666")
    println("第一次计算结果：$compute")
    val compute2 = cache.compute("666")
    println("第二次计算结果：$compute2")
}