package com.tck.kotlintrain.jsonparse

import com.google.gson.Gson
import com.squareup.moshi.Moshi

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

@Serializable
data class Person(val type:String,val name:String,val age:Int)

@ImplicitReflectionSerializer
fun main(){
    val gson = Gson()
    println(gson.toJson(Person("gson,","tck",30)))
    println(gson.fromJson("""{"name":"tck","age":30}""",Person::class.java))

    val moshi = Moshi.Builder().build()

    val jsonAdapter = moshi.adapter(Person::class.java)

    println(jsonAdapter.toJson(Person("moshi","tck",30)))


    println(Json.stringify(Person("ks","tck",30)))
}