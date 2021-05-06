package de.brainsizzle.twitchbotpaint.paint

import java.awt.Color

fun animateAll(shapes : List<Shape>) {
    for (shape in shapes) {
        animate(shape)
    }
}
fun animate(shape : Shape) {
    animatePosition(shape)
    animateRotation(shape)
    animateColor(shape)
}

fun animateColor(shape: Shape) {
    val animationsToRemove = mutableListOf<ColorAnimation>()
    for (colorAnimation in shape.colorAnimations) {
        if (colorAnimation.stepsRemaining > 0)
        {
            val red = shape.color.red.toDouble() + colorAnimation.rBitsPerStep;
            val green = shape.color.green.toDouble() + colorAnimation.gBitsForStep;
            val blue = shape.color.blue.toDouble() + colorAnimation.bBitsForStep;

            shape.color = Color(red.toInt(), green.toInt(), blue.toInt())
            colorAnimation.stepsRemaining--
        }
        else {
            animationsToRemove.add(colorAnimation)
        }
    }

    shape.colorAnimations.removeAll(animationsToRemove)
}

private fun animatePosition(shape: Shape) {
    val animationsToRemove = mutableListOf<PositionAnimation>()
    for (positionAnimation in shape.positionAnimations) {
        if (positionAnimation.stepsRemaining > 0) {
            shape.position.move(positionAnimation.positionOffSet)
            positionAnimation.stepsRemaining--
        } else {
            animationsToRemove.add(positionAnimation)
        }
    }
    shape.positionAnimations.removeAll(animationsToRemove)
}

private fun animateRotation(shape: Shape) {
    val animationsToRemove = mutableListOf<RotationAnimation>()
    for (rotationAnimation in shape.rotationAnimations) {
        if (rotationAnimation.stepsRemaining > 0) {
            shape.rotationDegress += rotationAnimation.degreesToRotatePerStep
            rotationAnimation.stepsRemaining--
        } else {
            animationsToRemove.add(rotationAnimation)
        }
    }
    shape.rotationAnimations.removeAll(animationsToRemove)
}