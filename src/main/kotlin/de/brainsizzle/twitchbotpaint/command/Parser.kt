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
    val doubleParams = mutableListOf<Double>()

    for (token in tokens) {
        val commandDefinition = matchToken(token)
        if (commandDefinition != null) {
            if (commandStarted != null) {
                // write started command to result
                result.add(fabricateCommand(commandStarted, intParams, doubleParams))
            }
            commandStarted = commandDefinition
            intParams.clear()
        } else {
            val intValue = parseIntParam(token)
            if (intValue != null) {
                intParams.add(intValue)
            } else {
                val doubleValue = parseDoubleParam(token)
                if (doubleValue != null) {
                    doubleParams.add(doubleValue)
                }
            }
        }
    }

    if (commandStarted != null) {
        result.add(fabricateCommand(commandStarted, intParams, doubleParams))
    }
    return result
}

fun parseIntParam(token: String): Int? {
    return token.toIntOrNull()
}

fun parseDoubleParam(token: String): Double? {
    return token.toDoubleOrNull()
}
private fun fabricateCommand(
    commandStarted: CommandDefinition?,
    intParams: MutableList<Int>,
    doubleParams: MutableList<Double>,
): Command {
    // future validate parameter signature

    // we need to de-couple the list
    return Command(commandStarted!!, intParams.toList(), doubleParams.toList())
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

const val allowedChars = "abcdefghijklmnopqrstuvwxzy0123456789-."

fun tokenise(cleanedCommand: String) : List<String> {
    return cleanedCommand.split(Pattern.compile("\\s+"))
}

