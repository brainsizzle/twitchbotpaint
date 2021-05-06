package de.brainsizzle.twitchbotpaint.paint

import java.awt.Color

data class Shape(val type: Type) {
    fun getRenderingColor(): Color {
        return color
    }

    fun setColor(red: Int, green: Int, blue: Int) {
        color = Color(red, green, blue)
    }

    fun setColor(rgb: Array<Int>) {
        color = Color(rgb[0], rgb[1], rgb[2])
    }

    var rotationDegress = 0.0
    var position: Position = Position(100.0, 100.0)
    var size: Int = 100
    var width: Int = 100
    var height: Int = 100
    var color: Color = Color.BLACK

    var animations = mutableListOf<Animation>()
}

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
