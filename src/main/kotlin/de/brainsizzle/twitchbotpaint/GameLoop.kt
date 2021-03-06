package de.brainsizzle.twitchbotpaint

import de.brainsizzle.twitchbotpaint.bot.BotRunner
import de.brainsizzle.twitchbotpaint.command.*
import de.brainsizzle.twitchbotpaint.console.ConsoleInput
import de.brainsizzle.twitchbotpaint.paint.*
import java.util.*

fun main() {
    val gameLoop = GameLoop()
    gameLoop.init()
}

val playGroundWidth = 200
val playGroundHeight = 600

class GameLoop : MessageCallback, ShapeLookup {

    private val userDisplayData = mutableMapOf<String, MutableList<Shape>>()
    private val staticShapes = mutableMapOf<String, Shape>()

    private var shapes = emptyList<Shape>()
    private var display : Display? = null

    fun init() {
        initDisplay()
        initCommands()
//        initDefaultShapes()

        initBotRunner()
        // initConsoleInput()

        initBouncingBall()

        println("got here")
        startLoop()
    }

    fun initBouncingBall() {
        val value = Shape(Type.Circle)
        value.size = 20
        value.animations.add(MoveReflectAnimation(Position(1.0, 1.0)))
        staticShapes.put("ball", value)
        updateShapes()
    }

    fun initDisplay() {
        display = Display(this, playGroundWidth, playGroundHeight)
    }

    fun startLoop() {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                animateAll(shapes)
                display?.canvas?.repaint()
            }
        }, 30, 20)
    }

    fun initBotRunner() {
        val botRunner = BotRunner(this)
        botRunner.init()
    }

    fun initConsoleInput() {
        val consoleInput = ConsoleInput(this)
        consoleInput.init()
    }

    fun initDefaultShapes() {
        //      to init canvas with any shape
        updateDisplayData(userDisplayData, "dumm1", parseCommands("square 80 col 0 125 255"))
        updateShapes()
        display?.canvas?.repaint()
    }

    override fun calcShapes(): List<Shape> {
        return shapes
    }

    override fun handlePaintMessage(userName: String, messagePayload: String): String? {
        try {
            var returnMessage: String? = null
            val parsedCommands = parseCommands(messagePayload)
            if (parsedCommands.isNotEmpty()) {

                returnMessage = printToChat(parsedCommands)

                updateDisplayData(userDisplayData, userName, parsedCommands)
                updateShapes()
                display?.canvas?.repaint()
            }
            return returnMessage
        }
        catch (ex: Exception) {
            println("caught in game loop: " + ex)
            return null
        }
    }

    private fun printToChat(parsedCommands: List<Command>): String? {
        val distinctPrintToChatCommandNames = parsedCommands
            .filter { command -> command.commandDefinition.commandType == CommandType.PrintToChat }
            .map { command -> command.commandDefinition.commandName }
            .distinct()
        for (commandName in distinctPrintToChatCommandNames) {
            if (commandName == CommandName.Help) {
                return printHelp()
            }
        }
        return null
    }

    private fun updateShapes() {
        val transformToShapes = transformToShapes(userDisplayData)
        transformToShapes.addAll(staticShapes.values)
        shapes = transformToShapes
    }

    private fun transformToShapes(userDisplayData: MutableMap<String, MutableList<Shape>>): MutableList<Shape> {
        val result = mutableListOf<Shape>()
        for (shapes in userDisplayData.values) {
            result.addAll(shapes)
        }
        return result
    }
}


