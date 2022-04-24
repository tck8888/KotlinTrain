package com.tck.kotlintrain.test

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType


interface IHttpProcessor {
    fun post(url: String, parameters: Map<String, String>)
}

class HttpHelper private constructor(): IHttpProcessor {
    override fun post(url: String, parameters: Map<String, String>) {
        httpProcessor?.post(url,parameters )
    }

    private var httpProcessor: IHttpProcessor? = null

    fun init(httpProcessor: IHttpProcessor?) {
        this.httpProcessor = httpProcessor
    }

    companion object {
        val instance:HttpHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            HttpHelper()
        }
    }


}

interface ICallBack {
    fun onSuccess(result: String)
    fun onFailure(errMsg: String)
}

abstract class HttpCallback<Result> : ICallBack {
    override fun onSuccess(result: String) {
        val analyzeClassInfo = analyzeClassInfo(this)
        val fromJson = Gson().fromJson<Result>(result, analyzeClassInfo)
        onSuccess(fromJson)
    }

    abstract fun onSuccess(data: Result)

    override fun onFailure(errMsg: String) {
        TODO("Not yet implemented")
    }


    private fun analyzeClassInfo(data: Any): Class<*> {
        val parameterizedType = data.javaClass.genericSuperclass as ParameterizedType
        val actualTypeArguments = parameterizedType.actualTypeArguments
        return actualTypeArguments[0] as Class<*>

    }
}


/**
 *
 * @author tck88
 *
 * @date 2021/5/12
 */
fun main() {

    for (index in 1 until 2) {
        println(index)
    }
}