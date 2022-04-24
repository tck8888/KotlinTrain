package com.tck.kotlintrain.lock

import java.util.concurrent.locks.ReentrantLock

/**
 *
 * @author tck88
 *
 * @date 2022-04-22
 */
class CinemaBookSeat {

     companion object{
         val lock = ReentrantLock()

         fun bookSeat() {
             lock.lock()
             try {
                 println("开始预订座位:${Thread.currentThread().name}")
                 Thread.sleep(1000)
                 println("座位完成:${Thread.currentThread().name}")
             } finally {
                 lock.unlock()
             }
         }
     }
}

fun main() {
    Thread{
        CinemaBookSeat.bookSeat()
    }.start()

    Thread{
        CinemaBookSeat.bookSeat()
    }.start()

    Thread{
        CinemaBookSeat.bookSeat()
    }.start()

    Thread{
        CinemaBookSeat.bookSeat()
    }.start()
}