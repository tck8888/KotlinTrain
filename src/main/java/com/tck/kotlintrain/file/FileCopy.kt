package com.tck.kotlintrain.file

import java.io.File

const val path = ""
const val KEYSTORE_PATH = ""

class FileCopy {

    val apkFiles = ArrayList<File>()

    fun getApkFiles(path: String) {
        val file = File(path)
        if (file.isFile) {
            if (file.name.endsWith(".apk")) {
                apkFiles.add(file)
            }
        } else {
            val listFiles = file.listFiles()
            listFiles?.forEach {
                if (it.isFile) {
                    if (it.name.endsWith(".apk")) {
                        apkFiles.add(it)
                    }
                } else {
                    getApkFiles(it.absolutePath)
                }
            }
        }
    }

}

class JiaGu {

    companion object {
        const val JIAGU_HOME = ""
        const val keyAlias = ""
        const val storePassword = ""
        const val keyPassword = ""
        const val storeFile = ""
        const val userName = ""
        const val pwd = ""
    }

    fun exec(apkFiles: List<File> = ArrayList<File>()) {
        val fileDir = File(path + File.separator + "jiagu")
        if (!fileDir.exists()) {
            fileDir.mkdir()
        }
        val file = File(fileDir, "apk_jiagu.bat")

        val stringBuilder = StringBuilder()
        stringBuilder.append("cd $path").append("\n").append("\n")
        stringBuilder.append("java -jar ${JIAGU_HOME}jiagu.jar -login $userName $pwd").append("\n")
            .append("\n")

        apkFiles.forEach {
            stringBuilder.append("java -jar ${JIAGU_HOME}jiagu.jar -importsign ${KEYSTORE_PATH}${storeFile} $storePassword $keyAlias $keyPassword")
                .append("\n").append("\n")

            stringBuilder.append("java -jar ${JIAGU_HOME}jiagu.jar -jiagu ${it.absolutePath} ${fileDir.absolutePath} -autosign")
                .append("\n").append("\n")
        }
        stringBuilder.append("pause")

        file.writeText(stringBuilder.toString())
    }


}

fun main() {

    val fileCopy = FileCopy()
    fileCopy.getApkFiles(path)

    println("扫描到的apk文件如下")
    fileCopy.apkFiles.forEach {
        println(it.absolutePath)
    }
    println("=====================================")
    println("=====================================")
    println("=====================================")
    println("=====================================")
    println("开始复制=============================")
    val fileDir = File(path)
    fileCopy.apkFiles.forEach {
        val targetFile = File(fileDir, it.name)
        it.copyTo(targetFile)
    }

    JiaGu().exec(fileCopy.apkFiles)
}