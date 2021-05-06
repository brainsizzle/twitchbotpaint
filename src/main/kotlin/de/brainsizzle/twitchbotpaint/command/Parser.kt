package de.brainsizzle.twitchbotpaint.command

import java.util.regex.Pattern

fun parseCommands(command: String) : List<Command> {
    val cleaned = clean(command)
    val tokens = tokenise(cleaned)
    return parseTokens(tokens)
}

fun parseTokens(tokens: List<String>): List<Command> {
    val result = mutableListOf<Command>()

    var commandStarted : CommandDefinition? = null
    val intParams = mutableListOf<Int>()

    for (token in tokens) {
        val commandDefinition = matchToken(token)
        if (commandDefinition != null) {
            if (commandStarted != null) {
                // write started command to result
                result.add(fabricateCommand(commandStarted, intParams))
            }
            commandStarted = commandDefinition
            intParams.clear()
        } else {
            val intValue = parseIntParam(token)
            if (intValue != null) {
                intParams.add(intValue)
            }
        }
    }
    if (commandStarted != null) {
        result.add(fabricateCommand(commandStarted, intParams))
    }
    return result
}

fun parseIntParam(token: String): Int? {
    return token.toIntOrNull()
}

private fun fabricateCommand(commandStarted: CommandDefinition?, parameterIntParams: MutableList<Int>): Command {
    // future validate parameter signature

    // we need to de-couple the list
    return Command(commandStarted!!, parameterIntParams.toList(), emptyList())
}

// all to lowercase and all non ascii letters or digits to spaces
fun clean(command: String): String {
    val cleaned = command.toLowerCase().trim()
    var result = ""
    for (char in cleaned) {
        if (allowedChars.contains(char)) {
            result += char
        } else {
            result += " "
        }
    }
    return result
}

const val allowedChars = "abcdefghijklmnopqrstuvwxzy0123456789"

fun tokenise(cleanedCommand: String) : List<String> {
    return cleanedCommand.split(Pattern.compile("\\s+"))
}

