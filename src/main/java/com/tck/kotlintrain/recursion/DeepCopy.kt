package com.tck.kotlintrain.recursion

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 *
 * @author tck88
 *
 * @date 2020/2/17
 */

data class Person(val id: Int, val name: String, val group: Group)

data class Group(val id: Int, val name: String, val location: String)

fun <T : Any> T.deepcopy(): T {
    if (!this::class.isData) {
        return this
    }

    return this::class.primaryConstructor!!.let { primaryConstructor ->
        primaryConstructor.parameters.map { parameter ->
            val value = (this::class as KClass<T>).memberProperties.first { it.name == parameter.name }
                .get(this)
            if ((parameter.type.classifier as? KClass <*>)?.isData == true) {
                parameter to value?.deepcopy()
            } else {
                parameter to value
            }
        }.toMap()
            .let(primaryConstructor::callBy)
    }
}
//https://github.com/enbandari/KotlinDeepCopy
fun main() {
    val person = Person(0, "tck", Group(0, "kotlin", "湖北"))

    val copiedPerson = person.copy()
    val deepCopiedPerson = person.deepcopy()

    println(person === copiedPerson)
    println(person === deepCopiedPerson)

    println(person.group === copiedPerson.group)
    println(person.group === deepCopiedPerson.group)
}
