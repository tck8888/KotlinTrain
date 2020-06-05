package com.tck.kotlintrain.javakotlin;

import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;

public class KClassTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("java.util.ArrayList");
        System.out.println(aClass);

        KClass<?> kotlinClass = JvmClassMappingKt.getKotlinClass(aClass);

        System.out.println(kotlinClass);

    }
}
