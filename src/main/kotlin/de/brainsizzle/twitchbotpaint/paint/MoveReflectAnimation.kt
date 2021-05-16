package de.brainsizzle.twitchbotpaint.paint

import de.brainsizzle.twitchbotpaint.playGroundHeight
import de.brainsizzle.twitchbotpaint.playGroundWidth

class MoveReflectAnimation(var positionOffSet : Position) : Animation {

    override fun animate(shape : Shape) : Boolean {

        if (shape.calcBoundingBoxRight() >= playGroundWidth) {
            positionOffSet.x *= -1
        }

        if (shape.calcBoundingBoxLeft() <= 0) {
            positionOffSet.x *= -1
        }

      if (shape.calcBoundingBoxTop() <= 0) {
            positionOffSet.y *= -1
        }

        if (shape.calcBoundingBoxBottom() >= playGroundHeight) {
            positionOffSet.y *= -1
        }

        shape.position.move(positionOffSet)

        return false
    }
}