package de.brainsizzle.twitchbotpaint.paint

import java.awt.Color

class ColorAnimation(val rBitsPerStep : Double, val gBitsForStep: Double, val bBitsForStep: Double, var stepsRemaining : Int) : Animation {

    override fun animate(shape : Shape) : Boolean {
            if (stepsRemaining > 0)
            {
                val red = shape.color.red.toDouble() + rBitsPerStep;
                val green = shape.color.green.toDouble() + gBitsForStep;
                val blue = shape.color.blue.toDouble() + bBitsForStep;

                shape.color = Color(red.toInt(), green.toInt(), blue.toInt())
                stepsRemaining--
                return false
            }
            return true
    }
}

