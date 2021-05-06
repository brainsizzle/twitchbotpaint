package de.brainsizzle.twitchbotpaint.paint

fun animateAll(shapes : List<Shape>) {
    for (shape in shapes) {
        animate(shape)
    }
}

fun animate(shape : Shape) {

    val animationsToRemove = mutableListOf<Animation>()
    if (shape.animations.size > 0) {
        println(shape.animations)
    }
    for (animation in shape.animations) {
        val remove = animation.animate(shape)
        if (remove) {
            animationsToRemove.add(animation)
        }
    }
    shape.animations.removeAll(animationsToRemove)
}
