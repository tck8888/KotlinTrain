package com.tck.kotlintrain.annotation

import java.awt.event.ActionListener
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActionListenerFor(val listener:KClass<out ActionListener>)