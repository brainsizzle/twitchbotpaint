package de.brainsizzle.twitchbotpaint.command

val commandDefinitions = mutableListOf<CommandDefinition>()

fun initCommands() {
    commandDefinitions.add(CommandDefinition(listOf("c", "circle"), CommandName.Circle, CommandType.Shape, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("n", "north"), CommandName.North, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("s", "south"), CommandName.South, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("a", "west"), CommandName.West, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("e", "east"), CommandName.East, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("color", "colour", "col"), CommandName.Color, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("delete", "del"), CommandName.Delete, CommandType.Delete, listOf(ParameterType.Solo)))
}

fun findByDefinitionByName(name: CommandName) : CommandDefinition? {
    for (commandDefinition in commandDefinitions)   {
        if (commandDefinition.commandName == name) {
            return commandDefinition
        }
    }
    return null
}

fun matchToken(token : String) : CommandDefinition? {
    for (commandDefinition in commandDefinitions)   {
        if (commandDefinition.matches.contains(token)) {
            return commandDefinition
        }
    }
    return null
}

data class CommandDefinition(
        val matches: List<String>,
        val commandName: CommandName,
        val commandType : CommandType,
        val parameterTypes: List<ParameterType>)

enum class CommandName {
    Circle,
    North,
    South,
    East,
    West,
    Color,
    Delete
}

enum class CommandType {
    Shape,
    Edit,
    Delete
}

enum class ParameterType{
    Solo,
    WithInt
}