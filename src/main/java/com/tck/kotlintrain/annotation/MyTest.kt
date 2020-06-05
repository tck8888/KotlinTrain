package com.tck.kotlintrain.annotation

import java.lang.IllegalArgumentException

@Target(AnnotationTarget.FUNCTION)
annotation class Testable


class MyTest {

    @Testable
    fun m1() {

    }

    fun m2() {

    }

    @Testable
    fun m3() {
        throw IllegalArgumentException("参数错误")
    }


    @Testable
    fun m5() {

    }

    fun m6() {

    }

    @Testable
    fun m7() {
        throw IllegalArgumentException("业务错误")
    }
    fun m8() {

    }

}