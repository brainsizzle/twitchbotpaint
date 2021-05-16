package de.brainsizzle.twitchbotpaint

interface MessageCallback {
    fun handlePaintMessage(userName: String, messagePayload: String) : String?
}