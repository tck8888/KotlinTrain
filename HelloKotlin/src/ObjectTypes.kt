/**
 *
 *   Description:对象
 *   @author:tck
 *   @date:2019/5/28
 *
 **/

class Girl(sound:String):Person(sound){

}

open class Person(var sound:String){
    init {
        println("===$sound")
    }
}

fun main(args:Array<String>){
    val girl =  Girl("甜美")
}