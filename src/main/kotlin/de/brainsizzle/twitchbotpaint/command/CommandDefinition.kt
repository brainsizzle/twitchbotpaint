package de.brainsizzle.twitchbotpaint.command

val commandDefinitions = mutableListOf<CommandDefinition>()

fun initCommands() {
    commandDefinitions.add(CommandDefinition(listOf("circle"), CommandName.Circle, CommandType.Shape, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("line"), CommandName.Line, CommandType.Shape, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("up", "u"), CommandName.Up, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("down", "d"), CommandName.Down, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("left", "l"), CommandName.Left, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("right", "r"), CommandName.Right, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("color", "colour", "col"), CommandName.Color, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("delete", "del"), CommandName.Delete, CommandType.Delete, listOf(ParameterType.Solo)))
    commandDefinitions.add(CommandDefinition(listOf("rotate", "rot"), CommandName.Rotate, CommandType.Edit, listOf(ParameterType.Solo, ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("scale"), CommandName.Scale, CommandType.Edit, listOf(ParameterType.WithDouble)))
    commandDefinitions.add(CommandDefinition(listOf("help"), CommandName.Help, CommandType.PrintToChat, listOf(ParameterType.Solo)))
    commandDefinitions.add(CommandDefinition(listOf("square"), CommandName.Square, CommandType.Shape, listOf(ParameterType.WithInt)))
    commandDefinitions.add(CommandDefinition(listOf("rectangle"), CommandName.Rectangle, CommandType.Shape, listOf(ParameterType.WithInt, ParameterType.WithInt)))
}

fun printHelp() : String {
    return commandDefinitions.joinToString(", ") { commandDefinition -> commandDefinition.matches.first() }
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
    Help,
    Scale,
    Square,
    Rectangle,
}

enum class CommandType {
    Shape,
    Edit,
    Delete,
    PrintToChat,
}

enum class ParameterType{
    Solo,
    WithInt,
    WithDouble,
}