package de.brainsizzle.twitchbotpaint.paint

import java.awt.Color

data class Shape(val type: Type) {
    // todo animations for color
    fun getRenderingColor(): Color {
        return color
    }

    // todo target position, move speed  for animations
    fun moveY(delta: Int) {
        position.y -= delta
    }

     fun moveX(delta: Int) {
        position.x -= delta
    }

    fun setColor(red: Int, green: Int, blue: Int) {
        color = Color(red, green, blue)
    }

    var position: Position = Position(100.0, 100.0)
    var size: Int = 100
    var color: Color = Color.BLACK

    var positionAnimations = mutableListOf<PositionAnimation>()
}

data class PositionAnimation(val positionOffSet : Position, var stepsRemaining : Int)

enum class Type {
    Circle
}

data class Position(var x: Double, var y: Double) {

    // todo later animations
    fun getXCoordinate() : Int {
        return x.toInt()
    }
    fun getYCoordinate() : Int {
        return y.toInt()
    }

    fun move(positionOffSet: Position) {
        x += positionOffSet.x
        y += positionOffSet.y
    }
}
