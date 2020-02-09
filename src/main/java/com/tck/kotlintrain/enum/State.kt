package com.tck.kotlintrain.enum

enum class State {
    Idle, Busy
}

fun State.next():State{
    return State.values().let {
        val nextOr=(ordinal+1)%it.size
        it[nextOr]
    }
}
enum class State1(val id: Int) {
    Idle(0), Busy(1)
}

enum class StateRunnable : Runnable {
    Idle, Busy {
        override fun run() {
            println("busy")
        }
    };

    override fun run() {
        println(",,,,,,,")
    }
}

fun main() {
    StateRunnable.Idle.run()
    StateRunnable.Busy.run()
}

