package de.brainsizzle.twitchbotpaint.paint;

public class RotateAnimation implements Animation {

    double degreesToRotatePerStep;
    int stepsRemaining;

    public RotateAnimation(double degreesToRotatePerStep, int stepsRemaining) {
        this.degreesToRotatePerStep = degreesToRotatePerStep;
        this.stepsRemaining = stepsRemaining;
    }

    @Override
    public boolean animate(Shape shape) {
        System.out.println("rotating " + stepsRemaining + " " + degreesToRotatePerStep);
        if (stepsRemaining > 0) {
            shape.setRotationDegress(shape.getRotationDegress() + degreesToRotatePerStep);
            stepsRemaining--;
            return false;
        }
        return true;
    }

}
