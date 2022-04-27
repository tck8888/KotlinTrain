package com.tck.kotlintrain.genericity

/**
 *
 * @author tck88
 *
 * @date 2022-04-25
 */
open class Animal
class Cat : Animal()

fun foo(list: MutableList<Animal>) {
    list.add(Cat())
    val animal = list[0]
}

fun foo2(list: MutableList<Cat>) {
    list.add(Cat())
    val animal = list[0]
}


open class Food

class KFC : Food()

class Restaurant<T> {
    fun orderFood() { /*..*/
    }
}

fun orderFood(restaurant: Restaurant<out Food>) {
    val food = restaurant.orderFood()
}
fun test(){
    val kfc = Restaurant<KFC>()
    orderFood(kfc)
}


open class TV {
    open fun turnOn() {}
}

class XiaoMiTV1: TV() {
    override fun turnOn() {}
}

class Controller<in T> {
    fun turnOn(tv: T){}
}

fun test2(tv:TV){

}
fun test3(){
    test2(XiaoMiTV1())
}

fun buy(controller: Controller<XiaoMiTV1>){
    val xiaoMiTV1 = XiaoMiTV1()
    controller.turnOn(xiaoMiTV1)
}

fun test4(){
    val controller=Controller<TV>()
    buy(controller)
}
// kotlin 协变 out  java extends
//kotlin 逆变 in  java super



sealed class Result<out R> {
    //                     协变    ①
//                      ↓      ↓
    data class Success<out T>(val data: T, val message: String = "") : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    data class Loading(val time: Long = System.currentTimeMillis()) : Result<Nothing>()
}