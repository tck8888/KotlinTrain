package com.tck.kotlintrain.clazz



abstract class Animal

class Person1(var age:Int,var name:String):Animal()

class Person2(var age:Int,var name:String):Animal(){
    constructor(age:Int):this(age,"unknown")
}