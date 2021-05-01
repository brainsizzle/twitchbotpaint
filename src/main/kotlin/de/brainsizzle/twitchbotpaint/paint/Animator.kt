package de.brainsizzle.twitchbotpaint.paint

fun animateAll(shapes : List<Shape>) {
    for (shape in shapes) {
        animate(shape)
    }
}
fun animate(shape : Shape) {
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