package de.brainsizzle.twitchbotpaint.paint

class RotateAnimation(var degreesToRotatePerStep: Double, var stepsRemaining: Int) : Animation {

    override fun animate(shape: Shape): Boolean {
        if (stepsRemaining > 0) {
            shape.rotationDegress = shape.rotationDegress + degreesToRotatePerStep
            stepsRemaining--
            return false
        }
        return true
    }
}