package com.tck.kotlintrain.genericity


class User<out T>{
    val info :T

    constructor(info:T){
        this.info=info
    }

    fun test():T{
        println("执行test方法")
        return info
    }
}

fun main() {
    var user = User<String>("tck")
    println(user.info)

    var u2:User<Any> =user
    u2.test()
}