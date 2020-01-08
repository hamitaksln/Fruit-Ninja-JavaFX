package view;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.time.Duration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Particle {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 800;
    public int secondPassed = 0;
    public AnimationTimer particleTimer;
    public AnimationTimer particleOpacityTimer;
    public final int size = 25;
    public final Circle[] circles = new Circle[size];
    public final long[] delays = new long[size];
    public final double[] angles = new double[size];
    public final long duration = Duration.ofSeconds(3).toNanos();
    public final Random random = new Random();



    public Particle()
    {

    }

    public void createParticles(Pane gamePane, int x, int y,Color fruit)
    {
        for (int i = 0; i < size; i++) {
            circles[i] = new Circle(3, 3,1, fruit);
            delays[i] = (long) (Math.random()*duration);
            angles[i] = 2 * Math.PI * random.nextDouble();
            circles[i].setCenterX(x);
            circles[i].setCenterY(y);
        }
        gamePane.getChildren().addAll(circles);

        particleTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                final double width = 0.5 * WIDTH;
                final double height = 0.5 * HEIGHT;
                final double radius = Math.sqrt(2) * Math.max(width, height);

                for (int i = 0; i < size; i++) {

                    Circle r = circles[i];
                    double angle = angles[i];
                    long t = (now - delays[i]) % duration;
                    double d = t*radius/duration/3;

                    circles[i].setOpacity((duration - t)/(double)duration);

                    //r.setOpacity(r.getOpacity()-0.4);

                    circles[i].setCenterX(Math.cos(angle)*d + x);
                    circles[i].setCenterY(Math.sin(angle)*d + y);
                    circles[i].setCenterY(circles[i].getCenterY()+10);

                    if(secondPassed == 1 )
                    {
                        r.setOpacity(r.getOpacity()-0.7);

                    }
                    //r.setTranslateX(r.getTranslateX()+1);
                    //r.setTranslateX(r.getTranslateX()+1);
                }
            }
        };
        particleTimer.start();

        Timer timee = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                secondPassed++;
                //System.out.println(secondPassed);
                if(secondPassed == 2) {
                    secondPassed = 0;
                    particleTimer.stop();
                    //setOpacity();
                    timee.cancel();
                    for (int i = 0; i <size ; i++) {
                        circles[i].setVisible(false);
                    }
                }
            }
        };
        timee.scheduleAtFixedRate(task,1000,1000);

    }

    public void setOpacity()
    {
        particleOpacityTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i <size ; i++) {
                    Circle r = circles[i];

                    r.setOpacity(r.getOpacity()-0.0005);
                }
            }
        };
        particleOpacityTimer.start();
    }
}
