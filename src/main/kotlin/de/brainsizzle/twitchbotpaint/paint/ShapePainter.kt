package de.brainsizzle.twitchbotpaint.paint

import java.awt.BasicStroke
import java.awt.Graphics2D

fun drawShape(g2: Graphics2D, shape: Shape) {
    when(shape.type) {
        Type.Circle -> drawCircle(g2, shape)
    }
}

fun drawCircle(g2: Graphics2D, shape: Shape) {
    g2.color = shape.getRenderingColor()
    g2.stroke = BasicStroke(3.0f)
    g2.drawOval(
            calcCenter(shape.position.getXCoordinate(), shape.size),
            calcCenter(shape.position.getYCoordinate(), shape.size),
            shape.size, shape.size)
}

fun calcCenter(coordinate: Int, size: Int): Int {
    return coordinate - size / 2
}
