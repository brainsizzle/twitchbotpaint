package de.brainsizzle.twitchbotpaint.paint;

import java.awt.*;

public class CirclePainter {

    public void drawCircle(Graphics2D g2, Shape shape) {
        g2.setColor(shape.getRenderingColor());
        g2.setStroke(new BasicStroke(3.0f));
        g2.drawOval(
                calcCenter(shape.getPosition().getXCoordinate(), shape.getSize()),
                calcCenter(shape.getPosition().getYCoordinate(), shape.getSize()),
                shape.getSize(), shape.getSize()
        );
    }

    public int calcCenter(int coordinate, int size) {
        return coordinate - size / 2;
    }

}
