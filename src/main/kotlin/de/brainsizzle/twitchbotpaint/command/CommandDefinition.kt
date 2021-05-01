package de.brainsizzle.twitchbotpaint.command

val commandDefinitions = mutableListOf<CommandDefinition>()

fun initCommands() {
    commandDefinitions.add(CommandDefinition(listOf("circle"), CommandName.Circle, CommandType.Shape, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("line"), CommandName.Line, CommandType.Shape, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("u", "up"), CommandName.Up, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("d", "down"), CommandName.Down, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("l", "left"), CommandName.Left, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("r", "right"), CommandName.Right, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("color", "colour", "col"), CommandName.Color, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("delete", "del"), CommandName.Delete, CommandType.Delete, listOf(ParameterType.Solo)))
    commandDefinitions.add(CommandDefinition(listOf("rot", "rotate"), CommandName.Rotate, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
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
    Line,
    Up,
    Down,
    Right,
    Left,
    Color,
    Delete,
    Rotate,
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