package de.brainsizzle.twitchbotpaint.bot

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import de.brainsizzle.twitchbotpaint.MessageCallback

class BotRunner(private val messageCallback: MessageCallback) {

    fun init() {
        println("starting bot")

        val config = loadConfig()

        val clientBuilder = TwitchClientBuilder.builder()

        val credential = OAuth2Credential(
                "twitch",
                config.credentials.irc
        )

        val twitchClient = clientBuilder
                .withClientId(config.api.twitch_client_id)
                .withClientSecret(config.api.twitch_client_secret)
                .withEnableHelix(true) /*
                 * Chat Module
                 * Joins irc and triggers all chat based events (viewer join/leave/sub/bits/gifted subs/...)
                 */
                .withChatAccount(credential)
                .withEnableChat(true) /*
                 * GraphQL has a limited support
                 * Don't expect a bunch of features enabling it
                 */
                .withEnableGraphQL(true) /*
                 * Kraken is going to be deprecated
                 * see : https://dev.twitch.tv/docs/v5/#which-api-version-can-you-use
                 * It is only here so you can call methods that are not (yet)
                 * implemented in Helix
                 */
                .withEnableKraken(true) /*
                 * Build the TwitchClient Instance
                 */
                .build()

        registerFeatures(twitchClient)
        start(config, twitchClient)


        println("finished")
    }

    fun start(config: Config, twitchClient: TwitchClient) {
        for (channel in config.channels) {
            twitchClient.getChat().joinChannel(channel)
        }
    }

    private fun registerFeatures(twitchClient: TwitchClient) {
        val eventHandler: SimpleEventHandler = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)

        eventHandler.onEvent(ChannelMessageEvent::class.java) {
            event: ChannelMessageEvent -> onChannelMessage(event)
        }
    }

    private fun onChannelMessage(event: ChannelMessageEvent) {
        val message = event.message
        System.out.printf(
                "Channel [%s] - User[%s] - Message [%s]%n",
                event.channel.name,
                event.user.name,
                message
        )
        val prefix = "!"
        if (message != null && message.startsWith(prefix)) {
            val returnMessage = messageCallback.handlePaintMessage(event.user.name, message.substring(prefix.lastIndex))
            if (returnMessage != null) {
                event.twitchChat.sendMessage(event.channel.name, returnMessage);
            }
        }
    }

    private fun loadConfig(): Config {
        val classloader = Thread.currentThread().contextClassLoader
        val `is` = classloader.getResourceAsStream("config.yaml")
        val yamlMapper = YAMLMapper.builder().addModule(kotlinModule()).build()
        return yamlMapper.readValue(`is`, Config::class.java)
    }
}