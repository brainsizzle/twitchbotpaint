package de.brainsizzle.twitchbotpaint.paint

import java.awt.BasicStroke
import java.awt.Graphics2D

fun drawShape(g2: Graphics2D, shape: Shape) {
    when (shape.type) {
        Type.Circle -> CirclePainter().drawCircle(g2, shape)
        Type.Line -> drawLine(g2, shape)
        Type.Square -> drawSquare(g2, shape)
        Type.Rectangle -> drawRectangle(g2, shape)
    }
}

fun drawRectangle(g2: Graphics2D, shape: Shape) {
    g2.color = shape.getRenderingColor()
    g2.stroke = BasicStroke(3.0f)
    g2.drawRect(
        calcCenter(shape.position.getXCoordinate(), shape.width),
        calcCenter(shape.position.getYCoordinate(), shape.height),
        shape.width, shape.height
    )
}

fun drawSquare(g2: Graphics2D, shape: Shape) {
    g2.color = shape.getRenderingColor()
    g2.stroke = BasicStroke(3.0f)

    val previousTransform = g2.transform

    val position = shape.position
    val xCenter = calcCenter(position.getXCoordinate(), shape.size)
    val yCenter = calcCenter(position.getYCoordinate(), shape.size)
    g2.rotate(Math.toRadians(shape.rotationDegress),  position.x, position.y)

    g2.drawRect(
        xCenter,
        yCenter,
        shape.size, shape.size
    )

    g2.transform = previousTransform
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

fun calcCenter(coordinate: Int, size: Int): Int {
    return coordinate - size / 2
}
