/**
 *<p>description:</p>
 *<p>created on: 2019/5/29 12:46</p>
 * @author tck
 * @version 1.0
 *
 */

val arrInt:IntArray = intArrayOf(1,3,4,5,6)

//val charArr:CharArray

fun main(args:Array<String>){
    println(arrInt.size)
    for (i in arrInt){
        println("i=$i")
    }
    println(arrInt[4])
    println(arrInt.slice(1..2))
}