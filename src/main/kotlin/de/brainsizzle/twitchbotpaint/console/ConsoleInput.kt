package de.brainsizzle.twitchbotpaint.console

import de.brainsizzle.twitchbotpaint.MessageCallback
import kotlin.concurrent.thread

class ConsoleInput(private val messageCallback: MessageCallback) {

    fun init() {
        thread {
            inputLoop()
        }
    }

    fun inputLoop() {
        println("starting input thread")
        while (true) {

            val readLine = readLine()
            if (readLine != null) {
                val handlePaintMessage = messageCallback.handlePaintMessage("console", readLine)
                if (handlePaintMessage != null) {
                    println(handlePaintMessage)
                }
            }
        }
    }
}