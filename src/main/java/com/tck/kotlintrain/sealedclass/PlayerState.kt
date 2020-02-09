package com.tck.kotlintrain.sealedclass
//密封类
//抽象类
//构造器私有
sealed class PlayerState

object Idle:PlayerState()

class Song
class ErrorInfo

class Playing(val song:Song):PlayerState()

class Error(val errorInfo:ErrorInfo):PlayerState()

//          密封类     枚举类
//状态实现   子类继承   类实例化
//状态可数    子类可数  实例可数
//状态差异    类型差异   值差异