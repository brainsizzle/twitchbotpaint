package de.brainsizzle.twitchbotpaint.paint

import de.brainsizzle.twitchbotpaint.ShapeLookup
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JPanel

class Canvas(val shapeLookup: ShapeLookup) : JPanel(true) {

    override fun paint(g: Graphics) {
        try {

            val g2 = g as Graphics2D
            g2.color = Color(150, 230, 230)
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