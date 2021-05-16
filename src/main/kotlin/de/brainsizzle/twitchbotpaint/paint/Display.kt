package de.brainsizzle.twitchbotpaint.paint

import de.brainsizzle.twitchbotpaint.ShapeLookup
import java.awt.*
import javax.swing.JFrame

class Display(shapeLookup : ShapeLookup, width : Int, height : Int) {

    val canvas = Canvas(shapeLookup)

    init {
        val frame = JFrame("twitchbotpaint")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isUndecorated = true

        position(frame)

        frame.setSize(200, 600)
        canvas.preferredSize = Dimension(width, height)

        frame.contentPane.add(canvas, BorderLayout.CENTER)
        frame.pack()
        frame.isVisible = true
    }

    private fun position(frame: JFrame) {
        var x = 0
        var y = 0

        val graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val arrayOfGraphicsDevices = graphicsEnvironment.screenDevices

        // find top left of leftmost screen
        for (graphicsDevice in arrayOfGraphicsDevices) {
            for (graphicsConfiguration in graphicsDevice.configurations) {
                if (graphicsConfiguration.bounds.x < x) {
                    x = graphicsConfiguration.bounds.x + 5
                    y = graphicsConfiguration.bounds.y + 5
                }
            }
        }

        frame.setLocation(x, y)
    }
}