package de.brainsizzle.twitchbotpaint.paint

class MoveAnimation(var positionOffSet : Position, var stepsRemaining : Int) : Animation {

    override fun animate(shape : Shape) : Boolean {
        if (stepsRemaining > 0) {
            MoveReflectAnimation(positionOffSet).animate(shape)
            stepsRemaining--
            return false
        }
        return true
    }
}