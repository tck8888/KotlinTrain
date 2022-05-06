package com.tck.kotlintrain.juc.thread

import java.util.logging.Level
import java.util.logging.Logger

/**
 *
 * @author tck88
 *
 * @date 2022-05-06
 */
class OwnUncaughtExceptionHandler(private val name:String):Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        val anonymousLogger = Logger.getAnonymousLogger()
        anonymousLogger.log(Level.WARNING,"线程异常，终止啦:${t?.name}",e)
        println("$name 捕获了异常：${t?.name}")
    }
}




object UserOwnUncaughtExceptionHandler{

    class Task :Runnable{
        override fun run() {
            throw RuntimeException()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        Thread.setDefaultUncaughtExceptionHandler(OwnUncaughtExceptionHandler("捕获器1"))
       Thread(Task()).start()
       Thread(Task()).start()
       Thread(Task()).start()
       Thread(Task()).start()
       Thread(Task()).start()
    }

}