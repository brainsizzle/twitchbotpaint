package de.brainsizzle.twitchbotpaint

import de.brainsizzle.twitchbotpaint.command.Command
import de.brainsizzle.twitchbotpaint.command.CommandName
import de.brainsizzle.twitchbotpaint.command.CommandType
import de.brainsizzle.twitchbotpaint.paint.*

fun updateDisplayData(userDisplayData: MutableMap<String, MutableList<Shape>>, user: String, commands: List<Command>) {
    val existingShapes = userDisplayData.getOrPut(user, { mutableListOf() })
    var activeShape = existingShapes.lastOrNull()
    for (command in commands) {
        if (CommandType.Shape == command.commandDefinition.commandType) {
           activeShape = fabricateShape(command)
            existingShapes.add(activeShape)
        } else if (activeShape != null) {
            if (CommandType.Edit == command.commandDefinition.commandType) {
                applyEdit(command, activeShape)
            } else if (CommandType.Delete == command.commandDefinition.commandType) {
                existingShapes.remove(activeShape)
                activeShape = null
            }
        }
    }
}

fun applyEdit(command: Command, shape: Shape) {

    if (command.commandDefinition.commandName == CommandName.Up) {
        var delta = 30
        if (command.commandIntParameters.isNotEmpty()) {
            delta = limitInt(command.commandIntParameters[0], 10, 250)
        }
        shape.animations.add(MoveAnimation(Position(0.0, -delta/40.0), 40))
    } else if (command.commandDefinition.commandName == CommandName.Down) {
        var delta = 30
        if (command.commandIntParameters.isNotEmpty()) {
            delta = limitInt(command.commandIntParameters[0], 10, 250)
        }
        shape.animations.add(MoveAnimation(Position(0.0, delta/40.0), 40))
    } else if (command.commandDefinition.commandName == CommandName.Left) {
        var delta = 30
        if (command.commandIntParameters.isNotEmpty()) {
            delta = limitInt(command.commandIntParameters[0], 10, 150)
        }
        shape.animations.add(MoveAnimation(Position(-delta/40.0, 0.0), 40))
    } else if (command.commandDefinition.commandName == CommandName.Right) {
        var delta = 30
        if (command.commandIntParameters.isNotEmpty()) {
            delta = limitInt(command.commandIntParameters[0], 10, 150)
        }
        shape.animations.add(MoveAnimation(Position(delta/40.0, 0.0), 40))
    } else if (command.commandDefinition.commandName == CommandName.Color) {
        var red = 0
        var green = 0
        var blue = 0
        if (command.commandIntParameters.size > 0) {
            red = limitInt(command.commandIntParameters[0], 0, 255)
        }
        if (command.commandIntParameters.size > 1) {
            green = limitInt(command.commandIntParameters[1], 0, 255)
        }
        if (command.commandIntParameters.size > 2) {
            blue = limitInt(command.commandIntParameters[2], 0, 255)
        }
        shape.setColor(red, green, blue)
    } else if (command.commandDefinition.commandName == CommandName.Rotate) {
        var degrees = 45
        if (command.commandIntParameters.size > 0) {
            degrees = limitInt(command.commandIntParameters[0], 0, 720)
        }
        val element = RotateAnimation(degrees / 40.0, 40)
        shape.animations.add(element)
    } else if (command.commandDefinition.commandName == CommandName.Scale) {
        var factor = 1.2
        if (command.commandDoubleParameters.isNotEmpty()) {
            factor = limitDouble(command.commandDoubleParameters[0], -5.0, 5.0)
        } else if (command.commandIntParameters.isNotEmpty()) {
            factor = limitInt(command.commandIntParameters[0], -5, 5).toDouble()
        }
        shape.animations.add(ScaleAnimation((shape.size * factor - shape.size) / 40.0, 40))
    }
}

fun fabricateShape(command: Command): Shape {
    return when(command.commandDefinition.commandName) {
        CommandName.Circle -> fabricateCircle(command)
        CommandName.Line -> fabricateLine(command)
        CommandName.Square -> fabricateSquare(command)
        CommandName.Rectangle -> fabricateRectangle(command)
        else -> Shape(Type.Circle)
    }
}

fun fabricateRectangle(command: Command): Shape {
    val shape = Shape(Type.Rectangle)
    if (command.commandIntParameters.isNotEmpty())
    {
        shape.width = limitInt(command.commandIntParameters[0], 10, 150)
        shape.height = limitInt(command.commandIntParameters[1], 10, 150)
    }
    return shape
}

fun fabricateSquare(command: Command): Shape {
    val shape = Shape(Type.Square)
    if (command.commandIntParameters.isNotEmpty())
    {
        shape.size = limitInt(command.commandIntParameters[0], 10, 150)
    }
    return shape
}

fun fabricateCircle(command: Command): Shape {
    val shape = Shape(Type.Circle)
    if (command.commandIntParameters.isNotEmpty()) {
        shape.size = limitInt(command.commandIntParameters[0], 10, 150)
    }
    return shape
}

fun fabricateLine(command: Command): Shape {
    val shape = Shape(Type.Line)
    if (command.commandIntParameters.isNotEmpty()) {
        shape.size = limitInt(command.commandIntParameters[0], 10, 150)
    }
    return shape
}

fun limitInt(value: Int, minValue: Int, maxValue: Int): Int {
    if (value < minValue) return minValue
    if (value > maxValue) return maxValue
    return value
}

fun limitDouble(value: Double, minValue: Double, maxValue: Double): Double {
    if (value < minValue) return minValue
    if (value > maxValue) return maxValue
    return value
}

