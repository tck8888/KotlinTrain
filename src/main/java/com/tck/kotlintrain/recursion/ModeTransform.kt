package com.tck.kotlintrain.recursion

import java.lang.IllegalArgumentException
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 *
 * @author tck88
 *
 * @date 2020/2/17
 */


inline fun <reified From : Any, reified To : Any> From.mapAs(): To {

    return From::class.memberProperties.map { it.name to it.get(this) }
        .toMap()
        .mapAs()
}

inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {

    return To::class.primaryConstructor!!.let {
        it.parameters.map { parameter ->
            parameter to (this[parameter.name] ?: if (parameter.type.isMarkedNullable) null
            else throw IllegalArgumentException("${parameter.name} missing"))
        }.toMap()
            .let(it::callBy)
    }
}