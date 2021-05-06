package de.brainsizzle.twitchbotpaint.paint

class MoveAnimation(val positionOffSet : Position, var stepsRemaining : Int) : Animation {

    override fun animate(shape : Shape) : Boolean {
        if (stepsRemaining > 0) {
            shape.position.move(positionOffSet)
            stepsRemaining--
            return false
        }
        return true
    }
}