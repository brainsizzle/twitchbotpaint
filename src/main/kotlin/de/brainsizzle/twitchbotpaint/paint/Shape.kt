package de.brainsizzle.twitchbotpaint.paint

import java.awt.Color

data class Shape(val type: Type) {
    // todo animations for color
    fun getRenderingColor(): Color {
        return color
    }

    fun setColor(red: Int, green: Int, blue: Int) {
        color = Color(red, green, blue)
    }

    fun setColor(rgb: Array<Int>) {
        color = Color(rgb[0], rgb[1], rgb[2])
    }

    // todo rotation animation
    var rotationDegress = 0.0
    var position: Position = Position(100.0, 100.0)
    var size: Int = 100
    var width: Int = 100
    var height: Int = 100
    var color: Color = Color.BLACK

    var positionAnimations = mutableListOf<PositionAnimation>()
    var rotationAnimations = mutableListOf<RotationAnimation>()
    var colorAnimations = mutableListOf<ColorAnimation>()
}

data class PositionAnimation(val positionOffSet : Position, var stepsRemaining : Int)
data class RotationAnimation(val degreesToRotatePerStep : Double, var stepsRemaining : Int)
data class ColorAnimation(val rBitsPerStep : Double, val gBitsForStep: Double, val bBitsForStep: Double, var stepsRemaining : Int)

enum class Type {
    Circle,
    Line,
    Square,
    Rectangle
}

data class Position(var x: Double, var y: Double) {

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
