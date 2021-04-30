package de.brainsizzle.twitchbotpaint.bot

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

fun main() {
    println("starting")

    val config = loadConfig()
    println(config)

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

fun registerFeatures(twitchClient: TwitchClient) {
    val eventHandler: SimpleEventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler::class.java)

    eventHandler.onEvent(
        ChannelMessageEvent::class.java
    ) { event: ChannelMessageEvent -> onChannelMessage(event) }
}

fun onChannelMessage(event: ChannelMessageEvent) {
    System.out.printf(
        "Channel [%s] - User[%s] - Message [%s]%n",
        event.channel.name,
        event.user.name,
        event.message
    );
}

fun loadConfig(): Config {
    val classloader = Thread.currentThread().contextClassLoader
    val `is` = classloader.getResourceAsStream("config.yaml")
    val yamlMapper = YAMLMapper.builder().addModule(kotlinModule()).build()
    return yamlMapper.readValue(`is`, Config::class.java)
}
