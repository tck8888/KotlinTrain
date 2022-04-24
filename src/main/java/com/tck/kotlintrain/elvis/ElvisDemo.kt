package com.tck.kotlintrain

fun main() {

    var data: ElvisDemo? = null
    println(data?.mRunning?:false)
}

class ElvisDemo {

    var mRunning: Boolean = false
}