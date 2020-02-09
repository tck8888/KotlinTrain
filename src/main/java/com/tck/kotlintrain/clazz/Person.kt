package com.tck.kotlintrain.clazz

class Person(var age: Int, name: String) {

    var name: String
    val firstName: String?

    init {
        this.name = name
        firstName = name.split(" ")[0]
    }
}