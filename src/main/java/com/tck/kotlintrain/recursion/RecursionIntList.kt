package com.tck.kotlintrain.recursion


sealed class IntList {

    object Nil : IntList() {
        override fun toString(): String {
            return "Nil"
        }
    }

    data class Cons(val head: Int, val tail: IntList) : IntList() {
        override fun toString(): String {
            return "$head,$tail"
        }
    }

    fun joinToString(step: Char = ','): String {
        return when (this) {
            Nil -> "Nil"
            is Cons -> {
                "$head$step${tail.joinToString(step)}"
            }
        }
    }
}

operator fun IntList.component1(): Int? {
    return when (this){
        IntList.Nil -> null
        is IntList.Cons ->head
    }
}

operator fun IntList.component2(): Int? {
    return when (this){
        IntList.Nil -> null
        is IntList.Cons ->tail.component1()
    }
}

operator fun IntList.component3(): Int? {
    return when (this){
        IntList.Nil -> null
        is IntList.Cons ->tail.component2()
    }
}

fun IntList.sum(): Int {
    return when (this) {
        IntList.Nil -> 0
        is IntList.Cons -> head + tail.sum()
    }
}

fun intListOf(vararg ints: Int): IntList {
    return when (ints.size) {
        0 -> IntList.Nil
        else -> {
            IntList.Cons(
                ints[0],
                intListOf(*(ints.slice(1 until ints.size).toIntArray()))
            )
        }
    }
}

fun main() {

    val list = IntList.Cons(0, IntList.Cons(1, IntList.Cons(2, IntList.Cons(3, IntList.Nil))))

    val list2 = intListOf(0, 1, 2, 3)
    print(list2)
    print(list2.joinToString('-'))
    print(list2.sum())

    val(first,second,third)=list
    print(first)
    print(second)
    print(third)

    val(a,b)= listOf<Int>()
}