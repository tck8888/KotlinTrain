package com.tck.kotlintrain.clazz



abstract class Animal

class Person1(var age:Int,var name:String):Animal()

class Person2(var age:Int,var name:String):Animal(){
    constructor(age:Int):this(age,"unknown")
}

open class Raw{
    fun test(){
        println(" test 方法")
    }
}

class RawSub :Raw(){
    fun sub(){
        println(" sub 方法")
    }
}

fun Raw.info(){
    println("Raw.info")
}


fun main() {
    val t =Raw()
    t.info()

    val rs = RawSub()

    rs.test()
    rs.sub()
    rs.info()
}