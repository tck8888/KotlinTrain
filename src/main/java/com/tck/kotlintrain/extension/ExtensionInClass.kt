package com.tck.kotlintrain.extension


class A {
    fun bar() = println(" A 的bar 方法")
}

class B{
    fun baz()= println(" B 的baz 方法")

    fun A.foo(){
        bar()
        baz()
    }

    fun test(target: A){
        target.bar()
        target.foo()
    }
}

fun main() {
    val b=B()
    b.test(A())
}



