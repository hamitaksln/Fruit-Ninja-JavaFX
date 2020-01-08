package view;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public abstract class Bomb implements Sliceable {
    public double velX = 0, velY = 0.0, velY2 = 0.0;
    public final double MAX_SPEED = 5;
    public final double gravity = 0.1;
    public final static int CIRCLE_RADIUS = 5;
    public int totalScore = 0;
    public int life = 10;
    public boolean isGameOver = false;

    public abstract void createBomb(Pane gamePane);
    public abstract void setBombPosition(ImageView image);
    public abstract void jumpBomb(ImageView fruit);
    public abstract void fallBomb(ImageView fruit);
    public abstract void moveBomb();
    public abstract void stopGame(Boolean isGameStopped);

    @Override
    public void slice(Circle circle,Pane gamePane) {

    }

    public double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
