package com.tck.kotlintrain.annotation

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties


class AnnotationTest {

    val mainFrame = JFrame()

    @ActionListenerFor(listener = OkListener::class)
    val ok = JButton("确定")

    @ActionListenerFor(listener = CancelListener::class)
    val cancel = JButton("取消")

    fun init() {
        val jPanel = JPanel()
        jPanel.add(ok)
        jPanel.add(cancel)
        mainFrame.add(jPanel)
        processAnnotations(this)

        mainFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        mainFrame.pack()
        mainFrame.isVisible = true
    }

    private fun processAnnotations(obj: Any) {
        val kClass = obj::class
        for (prop in kClass.memberProperties) {
            val findAnnotation = prop.findAnnotation<ActionListenerFor>()
            val call = prop.call(obj)
            if (findAnnotation != null && call != null&&call is AbstractButton) {
                val listenerClazz= findAnnotation.listener
                val createInstance = listenerClazz.createInstance()
                call.addActionListener(createInstance)
            }

        }
    }
}

class OkListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        JOptionPane.showMessageDialog(null, "单机了确定按钮")
    }
}

class CancelListener : ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        JOptionPane.showMessageDialog(null, "单机了取消按钮")
    }
}

fun main() {
    AnnotationTest().init()
}