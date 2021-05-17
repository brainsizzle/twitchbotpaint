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

class GameLoop : MessageCallback, ShapeLookup {

    private val userDisplayData = mutableMapOf<String, MutableList<Shape>>()

    private var shapes = emptyList<Shape>()
    private var display : Display? = null

    private val playGround = PlayGround(480, 320)

    fun init() {
        initDisplay()
        initCommands()
//        initDefaultShapes()

        // initBotRunner()
        initConsoleInput()

        initBouncingBall()

        println("got here")
        startLoop()
    }

    fun initBouncingBall() {

        val topBoundary = Shape(Type.Square, CollisionMode.Collision)
        topBoundary.size = 12
        topBoundary.position = Position(playGround.getCenter().x, 40.0)
        topBoundary.stretchX = (playGround.width - 12).toDouble() / topBoundary.size.toDouble()
        topBoundary.fill = true
        playGround.staticShapes.put("topBoundary", topBoundary)

        val lowerBoundary = Shape(Type.Square, CollisionMode.Collision)
        lowerBoundary.size = 12
        lowerBoundary.position = Position(playGround.getCenter().x, (playGround.height - lowerBoundary.size - 4).toDouble())
        lowerBoundary.stretchX = (playGround.width - 12).toDouble() / lowerBoundary.size.toDouble()
        lowerBoundary.fill = true
        playGround.staticShapes.put("lowerBoundary", lowerBoundary)

        val value = Shape(Type.Circle)
        value.size = 20
        value.animations.add(MoveReflectAnimation(playGround, Position(1.0, 1.0)))
        playGround.staticShapes.put("ball", value)
        updateShapes()
    }

    fun initDisplay() {
        display = Display(this )
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
        updateDisplayData(userDisplayData, playGround, "dumm1", parseCommands("square 80 col 0 125 255"))
        updateShapes()
        display?.canvas?.repaint()
    }

    override fun calcShapes(): List<Shape> {
        return shapes
    }

    override fun getPlayGround(): PlayGround {
        return playGround
    }

    override fun handlePaintMessage(userName: String, messagePayload: String): String? {
        try {
            var returnMessage: String? = null
            val parsedCommands = parseCommands(messagePayload)
            if (parsedCommands.isNotEmpty()) {

                returnMessage = printToChat(parsedCommands)

                updateDisplayData(userDisplayData, playGround, userName, parsedCommands)
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
        transformToShapes.addAll(playGround.staticShapes.values)
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


