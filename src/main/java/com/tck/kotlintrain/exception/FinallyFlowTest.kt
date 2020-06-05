package com.tck.kotlintrain.exception


fun main() {
    var a =test()
    println(a)
}

fun test():Boolean{
    try {
        return true
    }finally {
        return false
    }
}