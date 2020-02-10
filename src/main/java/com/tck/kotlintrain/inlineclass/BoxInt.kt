package com.tck.kotlintrain.inlineclass

//内联类

inline class BoxInt(val value: Int) {
    operator fun inc(): BoxInt {
        return BoxInt(value + 1)
    }
}

inline class BoxInt2(val value: Int) : Comparable<Int> {
    operator fun inc(): BoxInt2 {
        return BoxInt2(value + 1)
    }

    override fun compareTo(other: Int): Int {
        return value.compareTo(other)
    }
}

//内联类实现无符号类型
//TODO:
//内联类模拟枚举
inline class State(val oridinal: Int) {
    companion object {
        val Idle = State(0)
        val Busy = State(1)
    }


    fun values() = arrayOf(Idle, Busy)

    val name: String
        get() = "dddd"
}
