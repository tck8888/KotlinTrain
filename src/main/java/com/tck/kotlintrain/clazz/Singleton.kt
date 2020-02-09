package com.tck.kotlintrain.clazz

object Singleton {
    @JvmStatic var x:Int=2
    @JvmStatic fun y(){
        println("hello Singleton")
    }
}

fun main(){
    println(Singleton.x)
    Singleton.y()
}