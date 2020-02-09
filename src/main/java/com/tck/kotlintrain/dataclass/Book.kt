package com.tck.kotlintrain.dataclass

/**
 * 数据类 不可被继承
 */
data class Book (val id:Long,val name:String,val author:Person)
data class Person (val id:Long,val name:String,val age:Int)

fun main(){
    val book = Book(0,"kotlin", Person(0,"tck",18))

    //val id = book.component1()

    val(id,name,author)=book
}