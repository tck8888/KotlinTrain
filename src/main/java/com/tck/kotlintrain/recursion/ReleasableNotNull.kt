package com.tck.kotlintrain.recursion

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

/**
 *
 * @author tck88
 *
 * @date 2020/2/17
 */

fun <T : Any> releasableNotNull() = ReleasableNotNull<T>()

class ReleasableNotNull<T : Any> : ReadWriteProperty<Any, T> {

    private var value: T? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
return value?:throw IllegalArgumentException("")

    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun isInitialized()=value!=null
    fun release(){
        value=null
    }
}

inline val KProperty0<*>.isInitialized:Boolean
get() {
    isAccessible=true
    return (this.getDelegate() as? ReleasableNotNull<*>)?.isInitialized()?:throw java.lang.IllegalArgumentException("dd")
}

fun KProperty0<*>.release(){
    isAccessible=true
    return (this.getDelegate() as? ReleasableNotNull<*>)?.release()?:throw java.lang.IllegalArgumentException("dd")

}

class Bitmap(val width: Int, val height: Int)
class Activity {
    private var bitmap by releasableNotNull<Bitmap>()
}