package com.tck.kotlintrain.test



sealed class Format
data class Print(val text: String) : Format()
object Newline : Format()

val string = listOf<Format>(Print("hello"), Newline, Print("kotlin"))

fun unsafeInterpreter(str: List<Format>) {
    str.forEach {
        when (it) {
            is Print -> print(it.text)
            is Newline -> println()
        }
    }
}

fun stringInterpreter(str: List<Format>) = str.fold("") { fullText, s ->
    when (s) {
        is Print -> fullText + s.text
        is Newline -> fullText + "\n"
    }
}

fun main() {

//    var list=null
//   // list?.add("")
//   // println(list?.isNotEmpty() ?: false)
//
//    println( list?.toString()?:"")
//
//    val data = 0xffffffff
//
//    println(data.javaClass.toString())
//
//
//    val str="我a你"
//    val set= setOf(str,str.toLowerCase())
//    println(set)

//    for (i in 2020 downTo 1995){
//        println(i)
//    }

//    val set=HashSet<Int>()
//    print(set.joinToString(","))

//    val height = 600
//    for (i in 0..height) {
//        if (i < height ) {
//            println(i)
//        }
//    }

  println(  Math.sin(Math.toRadians(30.0)))

}