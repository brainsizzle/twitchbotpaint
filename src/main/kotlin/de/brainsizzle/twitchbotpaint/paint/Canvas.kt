package de.brainsizzle.twitchbotpaint.paint

import de.brainsizzle.twitchbotpaint.ShapeLookup
import java.awt.*
import javax.swing.JPanel

class Canvas(val shapeLookup: ShapeLookup) : JPanel(true) {

    var color1 = Color(150, 230, 230)
    var color2 = Color(240, 170, 70)

    override fun paint(g: Graphics) {
        try {

            val g2 = g as Graphics2D

            g2.paint = GradientPaint(0.0f,0.0f, color1, this.width.toFloat(), this.height.toFloat(), color2)
            g2.fillRect(0, 0, this.width, this.height)
            setRenderingHints(g2)

            val calcShapes = shapeLookup.calcShapes()
            // beware of race condition at startup
            calcShapes.forEach { shape -> drawShape(g2, shape) }
        } catch (ex: Exception) {
            println("caught in paint loop: " + ex + ex.stackTraceToString())
        }
    }

    private fun setRenderingHints(g2: Graphics2D) {
        val rh = RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2.setRenderingHints(rh)
    }
}