package de.brainsizzle.twitchbotpaint.paint

import de.brainsizzle.twitchbotpaint.PlayGround

class MoveAnimation(val playGround: PlayGround, var positionOffSet : Position, var stepsRemaining : Int) : Animation {

    override fun animate(shape : Shape) : Boolean {
        if (stepsRemaining > 0) {
            MoveReflectAnimation(playGround, positionOffSet).animate(shape)
            stepsRemaining--
            return false
        }
        return true
    }
}