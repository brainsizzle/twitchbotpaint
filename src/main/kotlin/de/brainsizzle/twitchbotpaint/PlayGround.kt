package de.brainsizzle.twitchbotpaint

import de.brainsizzle.twitchbotpaint.paint.Position
import de.brainsizzle.twitchbotpaint.paint.Shape

data class PlayGround(val width: Int, val height: Int) {

    val staticShapes = mutableMapOf<String, Shape>()

    fun getCenter(): Position {
        return Position(width.toDouble() / 2.0, height.toDouble() / 2.0)
    }


}