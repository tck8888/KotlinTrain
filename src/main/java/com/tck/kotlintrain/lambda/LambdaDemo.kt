package com.tck.kotlintrain.lambda

/**
 *
 * @author tck88
 *
 * @date 2020/5/7
 */

fun a(funParam: (Int) -> String): String {
    return funParam(1)
}
