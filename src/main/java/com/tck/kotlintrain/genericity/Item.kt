package com.tck.kotlintrain.genericity


class Item<in T>{
    fun foo(t:T){
        println(t)
    }
}

fun main() {
    var item =Item<Any>()

    item.foo(200)

    var item2:Item<String> =item
    item2.foo("dsdsdds")
}