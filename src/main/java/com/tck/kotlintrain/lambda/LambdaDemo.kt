package com.tck.kotlintrain.lambda

import java.util.concurrent.TimeUnit
import kotlin.coroutines.suspendCoroutine

/**
 *
 * @author tck88
 *
 * @date 2020/5/7
 */

fun a(funParam: (Int) -> String): String {
    return funParam(1)
}


fun foo(x: Int): (Int) -> Int {
    return { y: Int -> x + y }
}

//柯里化
fun sum(x: Int, y: Int, z: Int) = x + y + z
fun sum(x: Int) = { y: Int -> { z: Int -> x + y + z } }

fun b(block: () -> Unit) {
    block()
}

fun c(content: String, block: (String) -> Unit) {
    block(content)
}


fun main() {
    sum(1, 2, 3)
    sum(1)(2)(3)

    b {
        println("b")
    }

    c("tck") { content ->
        print(content)
    }
    c("tck", { content ->
        print(content)
    })

    val list= arrayOf("1","1")

}

suspend fun delay(time:Long,unit:TimeUnit=TimeUnit.MILLISECONDS){
    if (time<=0){
        return
    }

    suspendCoroutine<Unit> {continuation ->   }

    println("delay $time")
}
