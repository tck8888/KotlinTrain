package com.tck.kotlintrain.json

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


const val jsonStr1 = """
    {
    "status":0,
    "errorCode":null,
    "errorMessage":null,
    "subCode":null,
    "subMessage":null,
    "projectCode":null,
    "data":{
        "workingHours":"工作日9:00-18:00",
        "serviceTelephone":"4003001021",
        "emailNotificationPrompt":"已邮件告知研究助理，请后续保持电话畅通，研究助理或客服将与您联系。",
        "check":false
    }
}
"""

val json="""
    {"subjectCode":"5f14637247fc48038cba208ca2c0471c16","id":9350,"refresh":true}
""".trimIndent()

class User(val subjectCode:String,val id:String?,val refresh:Boolean)
fun main() {

    val alpha = 254 shr 16
    println("alpha:${alpha.toByte()}")
    val fromJson = Gson().fromJson(json, User::class.java)
    println(fromJson.id)
    println(fromJson.subjectCode)
    println(fromJson.refresh)
}

//fun argb(alpha: Int, red: Int, green: Int, blue: Int):Int {
//    return (alpha << 24) or (red << 16) or (green << 8) or (blue)
//}