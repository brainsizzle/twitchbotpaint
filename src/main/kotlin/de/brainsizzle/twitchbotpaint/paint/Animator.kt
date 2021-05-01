package de.brainsizzle.twitchbotpaint.paint

fun animateAll(shapes : List<Shape>) {
    for (shape in shapes) {
        animate(shape)
    }
}
fun animate(shape : Shape) {
    animatePosition(shape)
    animateRotation(shape)
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