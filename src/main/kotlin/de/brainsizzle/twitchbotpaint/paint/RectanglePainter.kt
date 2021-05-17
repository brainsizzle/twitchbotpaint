package de.brainsizzle.twitchbotpaint.paint

import java.awt.BasicStroke
import java.awt.Graphics2D

class RectanglePainter {
    fun drawSquare(g2: Graphics2D, shape: Shape) {
        g2.color = shape.getRenderingColor()
        g2.stroke = BasicStroke(3.0f)

        val previousTransform = g2.transform

        val position = shape.position
        g2.rotate(Math.toRadians(shape.rotationDegress),  position.x, position.y)

        if (shape.fill) {
            g2.fillRect(
                shape.calcBoundingBoxLeft(),
                shape.calcBoundingBoxTop(),
                shape.getStretchedWidth(),
                shape.size)
        } else {
            g2.drawRect(
                shape.calcBoundingBoxLeft(),
                shape.calcBoundingBoxTop(),
                shape.getStretchedWidth(), shape.size
            )
        }

        g2.transform = previousTransform
    }
}