package com.tck.kotlintrain.inlineclass

//内部类
//内部类相当于java没有使用static修饰的内部类。使用inner关键字修饰。
//内部类成员可以直接访问外部类的私有数据，因为内部类相当于外部类的成员之一；
//外部类不能访问内部类的成员，如需访问，需要通过创建内部类对象，通过对象访问内部类成员。

class OuterClass {
    private val outerParameter = "外部类私有属性"
    private fun outerFun(): String {
        return "外部类私有函数"
    }

    fun invokingInnerClassFun() {
        println("外部类调用：${InnerClass().innerFun()}")
    }

    inner class InnerClass {
        fun innerFun(): String {
            return "内部类公有函数"
        }

        fun innerFun1(){
            println("内部类访问：${outerParameter}")
        }

        fun innerFun2(){
            println("内部类访问：${outerFun()}")
        }
    }
}

fun main(){
    OuterClass().invokingInnerClassFun()
    OuterClass().InnerClass().innerFun1()
    OuterClass().InnerClass().innerFun2()
}