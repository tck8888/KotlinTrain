package com.tck.kotlintrain.genericity



open class Apple <T>{
    open var info :T?
    constructor(){
        info=null
    }
}


class A :Apple<String>(){
    override var info: String?
        get() = "子类"+super.info
        set(value) {}
}