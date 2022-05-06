package com.tck.kotlintrain.juc.future

import java.io.File
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask

/**
 *
 * @author tck88
 *
 * @date 2022-04-29
 */
class FutureTaskDemo {

    fun test() {
        val task = Callable {
            var sum = 0
            for (i in 0..100) {
                sum += i
            }
            sum
        }
        val futureTask = FutureTask(task)

        val service = Executors.newFixedThreadPool(5)

        service.submit(futureTask)

        println(futureTask.get())

        service.shutdown()

        //CompletableFuture
    }

    fun test2() {
        val task = Callable {
            println("线程开始工作了")
            var sum = 0
            for (i in 0..100) {
                sum += i
            }
            sum
        }
        val futureTask = FutureTask(task)
        Thread(futureTask).start()
        println(futureTask.get())
    }
}

fun main() {
   // FutureTaskDemo().test2()
    println("""{"currentPicPath":"${File("D:\\java_project\\KotlinTrain\\src\\main\\java\\com\\tck\\kotlintrain\\annotation\\ActionListenerFor.kt").absolutePath}"}""")
}