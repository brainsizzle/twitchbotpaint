package de.brainsizzle.twitchbotpaint.bot

interface MessageCallback {
    fun handlePaintMessage(userName: String, messagePayload: String) : String?
}