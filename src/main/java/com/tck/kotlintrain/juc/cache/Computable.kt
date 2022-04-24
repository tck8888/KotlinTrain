package com.tck.kotlintrain.juc.cache

import kotlin.jvm.Throws

/**
 *
 * @author tck88
 *
 * @date 2022-04-21
 */
interface Computable<A, V> {

    @Throws(Exception::class)
    fun compute(arg: A): V
}