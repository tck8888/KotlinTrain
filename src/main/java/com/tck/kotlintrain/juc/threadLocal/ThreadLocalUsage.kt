package com.tck.kotlintrain.juc.threadLocal

data class User(var name: String)
class Service1 {

    fun process() {
        val user = User("tck6666")
        UserContextHolder.holder.set(user)
        Service2().process()
    }
}

class Service2 {

    fun process() {
        val get = UserContextHolder.holder.get()
        println("Service2:$get")
        Service3().process()
    }
}

class Service3 {

    fun process() {
        val get = UserContextHolder.holder.get()
        println("Service3:$get")
    }
}

class UserContextHolder {
    companion object {
        val holder = ThreadLocal<User>()
    }
}

fun main(){
    Service1().process()
}