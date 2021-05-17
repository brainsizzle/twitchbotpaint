package de.brainsizzle.twitchbotpaint

import de.brainsizzle.twitchbotpaint.paint.Shape

interface ShapeLookup {
    fun calcShapes() : List<Shape>
    fun getPlayGround() : PlayGround
}
