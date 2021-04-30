package de.brainsizzle.twitchbotpaint.paint

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import javax.swing.JFrame


fun main() {
    Display()
}

class Display {
    init {
        val frame = JFrame("twitchbotpaint")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isUndecorated = true
        val canvas = Canvas()
        canvas.setSize(400, 400)
        frame.add(canvas)
        frame.pack()
        frame.isVisible = true
    }

}
class Canvas : java.awt.Canvas() {
    override fun paint(g: Graphics) {
        val g2 = g as Graphics2D
        val rh = RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        rh[RenderingHints.KEY_ANTIALIASING] = RenderingHints.VALUE_ANTIALIAS_ON
        g2.setRenderingHints(rh)
        g.fillOval(100, 100, 200, 200)
    }
}