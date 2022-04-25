package com.tck.kotlintrain.juc.cas


/**
 *
 * @author tck88
 *
 * @date 2022-04-25
 */
class SimulatedCAS2 : Runnable {

    @Volatile
    var a = 0


    @Synchronized
    fun compareAndSwap(expect: Int, update: Int): Int {
        val oldValue = a
        if (oldValue == expect) {
            a = update
        }
        return oldValue
    }

    override fun run() {
        compareAndSwap(0, 1)
    }
}

//fun main() {
//    val simulatedCAS2 = SimulatedCAS2()
//    val t0 = Thread(simulatedCAS2)
//    val t1 = Thread(simulatedCAS2)
//    t0.start()
//    t1.start()
//    t0.join()
//    t1.join()
//    println("value:${simulatedCAS2.a}")
//}

fun main() {
    var a = "tck2"
    val b = "tck"
    var d = "tck"
    var c = b + 2
    var e = d + 2
    println(a==c)
    println(a==e)
}