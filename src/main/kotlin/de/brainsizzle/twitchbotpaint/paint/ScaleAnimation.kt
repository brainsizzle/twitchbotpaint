package de.brainsizzle.twitchbotpaint.paint

class ScaleAnimation(var factorPerStep: Double, var stepsRemaining: Int) : Animation {

    private var currentValue : Double? = null

    override fun animate(shape: Shape): Boolean {
        if (currentValue == null) {
            currentValue = shape.size.toDouble()
        }
        if (stepsRemaining > 0) {
            currentValue = currentValue!! * factorPerStep
            shape.size = currentValue!!.toInt()
            stepsRemaining--
            return false
        }
        return true
    }
}