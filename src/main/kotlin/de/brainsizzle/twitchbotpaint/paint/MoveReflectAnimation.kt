package de.brainsizzle.twitchbotpaint.paint

import de.brainsizzle.twitchbotpaint.PlayGround

class MoveReflectAnimation(val playGround: PlayGround, var positionOffSet : Position) : Animation {

    override fun animate(shape : Shape) : Boolean {

        val collisionWithBoundingBox = collisionWithBoundingBox(shape)

        if (!collisionWithBoundingBox) {

            val originPosition = shape.position.copy()

            val movedYPosition = shape.position.copy()
            movedYPosition.move(positionOffSet.copy(x = 0.0))

            val movedXPosition = shape.position.copy()
            movedXPosition.move(positionOffSet.copy(y = 0.0))

            shape.position = movedXPosition
            val boundingBoxMovedX = shape.getBoundingBox()

            shape.position = movedYPosition
            val boundingBoxMovedY = shape.getBoundingBox()

            shape.position = originPosition

            for (staticShape in playGround.staticShapes) {
                // self
                if (shape == staticShape.value) {
                    continue
                }
                if (staticShape.value.collisionMode != CollisionMode.Collision) {
                    continue
                }
                val otherBoundindBox = staticShape.value.getBoundingBox()
                if (boundingBoxMovedX.intersects(otherBoundindBox)) {
                    positionOffSet.x *= -1
                }
                if (boundingBoxMovedY.intersects(otherBoundindBox)) {
                    positionOffSet.y *= -1
                }
            }
        }

        shape.position.move(positionOffSet)


        return false
    }

    private fun collisionWithBoundingBox(shape: Shape) : Boolean {

        var collision = false
        if (shape.calcBoundingBoxRight() >= playGround.width) {
            positionOffSet.x *= -1
            collision = true
        } else if (shape.calcBoundingBoxLeft() <= 0) {
            positionOffSet.x *= -1
            collision = true
        }

        if (shape.calcBoundingBoxTop() <= 0) {
            positionOffSet.y *= -1
            collision = true
        } else if (shape.calcBoundingBoxBottom() >= playGround.height) {
            positionOffSet.y *= -1
            collision = true
        }
        return collision
    }
}