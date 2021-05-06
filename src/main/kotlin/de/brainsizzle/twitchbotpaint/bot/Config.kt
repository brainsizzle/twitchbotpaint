package de.brainsizzle.twitchbotpaint.bot

data class Config(
    val debug: Boolean,
    val bot: Bot,
    val api: Api,
    val credentials: Credentials,
    val channels: List<String>,
)

data class Bot(
    val name: String,
    val version: String
)

data class Api(
    val twitch_client_id: String,
    val twitch_client_secret: String,
)

data class Credentials(val irc: String)
