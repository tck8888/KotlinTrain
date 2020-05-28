package com.tck.kotlintrain.extension



val fac = fun Int.():Int{
    if (this<0){
        return  -1
    }else if (this==1){
        return 1
    }else{
        var  result=1
        for (i in 1..this){
            result*=i
        }

        return result
    }
}

fun main() {
    print(4.fac())
}