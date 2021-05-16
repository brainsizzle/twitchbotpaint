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
    var stretchX = 1.0
    var color: Color = Color.BLACK
    var fill = false

    var animations = mutableListOf<Animation>()

    fun calcBoundingBoxLeft() : Int {
        return position.getXCoordinate() - getHalfStretchedWidth()
    }

    fun calcBoundingBoxTop() : Int {
        return position.getYCoordinate() - size / 2
    }

    fun calcBoundingBoxRight() : Int {
        return position.getXCoordinate() + getHalfStretchedWidth()
    }

    fun calcBoundingBoxBottom() : Int {
        return position.getYCoordinate() + size / 2
    }

    fun getStretchedWidth() = (size * stretchX).toInt()

    fun getHalfStretchedWidth() = ((size * stretchX) / 2.0).toInt()


}

enum class Type {
    Circle,
    Line,
    Square,
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
