package com.tck.kotlintrain.annotation

import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.functions


inline fun <reified  T:Any> processTestable(){
    var passed=0
    var failed=0
    val target = T::class.createInstance<T>()
    for (m in T::class.functions){
        if (m.findAnnotation<Testable>()!=null){
            try {
                m.call(target)
                passed++
            } catch (e: Exception) {
                println("方法${m}运行失败，异常：${e.cause}")
                failed++
            }
        }
    }

    println("passed:${passed}")
    println("failed:${failed}")
}

fun main() {
    processTestable<MyTest>()
}