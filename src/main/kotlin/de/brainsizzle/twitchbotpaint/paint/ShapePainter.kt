package de.brainsizzle.twitchbotpaint.paint

import org.checkerframework.checker.units.qual.degrees
import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.geom.AffineTransform


fun drawShape(g2: Graphics2D, shape: Shape) {
    when(shape.type) {
        Type.Circle -> drawCircle(g2, shape)
        Type.Line -> drawLine(g2, shape)
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

fun drawLine(g2: Graphics2D, shape: Shape) {
    g2.color = shape.getRenderingColor()
    g2.stroke = BasicStroke(3.0f)

    val position = shape.position
    val previousTransform = g2.transform
    g2.rotate(Math.toRadians(shape.rotationDegress), position.x, position.y)

    g2.drawLine(
            position.getXCoordinate(),
            position.getYCoordinate() - shape.size / 2,
            position.getXCoordinate(),
            position.getYCoordinate() + shape.size / 2)
    g2.transform = previousTransform

}

fun calcCenter(coordinate: Int, size: Int): Int {
    return coordinate - size / 2
}
