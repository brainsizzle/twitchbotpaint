package de.brainsizzle.twitchbotpaint.paint

import java.awt.BasicStroke
import java.awt.Graphics2D

class CirclePainter {

    fun drawCircle(g2: Graphics2D, shape: Shape) {
        g2.color = shape.getRenderingColor()
        g2.stroke = BasicStroke(3.0f)

        if (shape.fill) {
            g2.fillOval(
                shape.calcBoundingBoxLeft(),
                shape.calcBoundingBoxTop(),
                shape.size, shape.size
            )
        } else {
            g2.drawOval(
                shape.calcBoundingBoxLeft(),
                shape.calcBoundingBoxTop(),
                shape.size, shape.size
            )
        }
    }
}