package de.brainsizzle.twitchbotpaint.paint

import java.awt.BasicStroke
import java.awt.Graphics2D

fun drawShape(g2: Graphics2D, shape: Shape) {
    when (shape.type) {
        Type.Circle -> CirclePainter().drawCircle(g2, shape)
        Type.Line -> drawLine(g2, shape)
        Type.Square -> RectanglePainter().drawSquare(g2, shape)
    }
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
        position.getYCoordinate() + shape.size / 2
    )
    g2.transform = previousTransform

}
