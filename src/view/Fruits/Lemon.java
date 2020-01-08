package view.Fruits;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import view.Fruit;
import view.Particle;
import view.Splash;

public class Lemon extends Fruit {

    private final static String LEMON_IMAGE = "view/resources/lemon.png";
    private final static String LEMON_SLICED_IMAGE_1 = "view/resources/lemon_sliced1.png";
    private final static String LEMON_SLICED_IMAGE_2 = "view/resources/lemon_sliced2.png";
    private final static int LEMON_RADIUS = 31;

    private ImageView[] lemon;
    private ImageView[] lemonSliced1;
    private ImageView[] lemonSliced2;

    Random randomPositionGenerator;
    private static boolean[] jumping = new boolean[25];
    private static boolean[] falled = new boolean[25];

    public AnimationTimer move;

    Particle splash = new Particle();

    Splash splashEffect = new Splash();


    public Lemon()
    {
        makeTrue();
        randomPositionGenerator = new Random();
    }
    private static void makeTrue() {
        for (int i = 0; i < jumping.length; i++) {
            jumping[i] = true;
        }
    }

    @Override
    public void createFruit(Pane gamePane) {

        lemon = new ImageView[25];

        for (int i = 0; i < lemon.length; i++) {
            lemon[i] = new ImageView(LEMON_IMAGE);
            setFruitPosition(lemon[i]);
            gamePane.getChildren().add(lemon[i]);
        }
    }

    @Override
    public void createSlicedFruit(Pane gamePane) {
        lemonSliced1 = new ImageView[25];
        lemonSliced2 = new ImageView[25];
        for (int i = 0; i < lemonSliced1.length; i++) {
            lemonSliced1[i] = new ImageView(LEMON_SLICED_IMAGE_1);
            setSlicedFruitPosition(lemonSliced1[i], (int) lemon[i].getLayoutX(), (int) lemon[i].getLayoutY());
            gamePane.getChildren().add(lemonSliced1[i]);
            lemonSliced1[i].setVisible(false);
        }

        for (int i = 0; i < lemonSliced2.length; i++) {
            lemonSliced2[i] = new ImageView(LEMON_SLICED_IMAGE_2);
            setSlicedFruitPosition(lemonSliced2[i], (int) lemon[i].getLayoutX() + 36, (int) lemon[i].getLayoutY() + 1);
            gamePane.getChildren().add(lemonSliced2[i]);
            lemonSliced2[i].setVisible(false);
        }
    }

    @Override
    public void setFruitPosition(ImageView image) {
        image.setLayoutY(randomPositionGenerator.nextInt((50000 - 900) + 1) + 900);
        image.setLayoutX(randomPositionGenerator.nextInt(200));
    }

    @Override
    public void setSlicedFruitPosition(ImageView image, int x, int y) {
        image.setLayoutX(x);
        image.setLayoutY(y);
    }

    @Override
    public void jumpFruit(ImageView fruit) {
        if (fruit.getLayoutY() < 850) {
            fruit.setLayoutX(fruit.getLayoutX() + 1);

        }

        fruit.setLayoutY(fruit.getLayoutY() - velY);
        fruit.setRotate(fruit.getRotate() + randomPositionGenerator.nextInt((4 - 1) + 1) + 1);

        velY = velY + gravity;
        if (velY > MAX_SPEED) {
            velY = MAX_SPEED;
        }
    }

    @Override
    public void fallFruit(ImageView fruit) {
        fruit.setLayoutX(fruit.getLayoutX() + 1);
        fruit.setLayoutY(fruit.getLayoutY() + velY2);
        fruit.setRotate(fruit.getRotate() + randomPositionGenerator.nextInt((4 - 1) + 1) + 1);

        velY2 = velY2 + gravity;
        if (velY2 > MAX_SPEED) {
            velY2 = MAX_SPEED;
        }
    }

    @Override
    public void moveFruit(Boolean isGameStopped) {
         move = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < lemon.length; i++) {
                    if (lemon[i].getLayoutY() > randomPositionGenerator.nextInt((300 - 100) + 1) + 100 & jumping[i] == true) {

                        jumpFruit(lemon[i]);
                    } else if(falled[i] == false){
                        jumping[i] = false;

                        fallFruit(lemon[i]);

                        if (lemon[i].getLayoutY() > 800) {
                            falled[i] = true;
                            life -= 1;
                            fixPosition(lemon[i]);

                        }
                    }

                }
            }
        };
        move.start();
    }

    public void fixPosition(ImageView image)
    {
            image.setLayoutX(1400);
            image.setLayoutY(400);
            image.setVisible(false);
    }

    @Override
    public void moveSliced() {
        for (int i = 0; i < lemonSliced1.length; i++) {
            lemonSliced1[i].setLayoutY(lemonSliced1[i].getLayoutY() + 3);
            lemonSliced1[i].setRotate(lemonSliced1[i].getRotate() - (randomPositionGenerator.nextInt((5 - 2) + 1) + 2));
            lemonSliced1[i].setLayoutX(lemonSliced1[i].getLayoutX() - 1);

        }
        for (int i = 0; i < lemonSliced2.length; i++) {

            lemonSliced2[i].setLayoutY(lemonSliced2[i].getLayoutY() + 3);
            lemonSliced2[i].setRotate(lemonSliced2[i].getRotate() + randomPositionGenerator.nextInt((5 - 2) + 1) + 2);
            lemonSliced2[i].setLayoutX(lemonSliced2[i].getLayoutX() + 1);

        }
    }
    @Override
    public void slice(Circle circle,Pane gamePane) {
        super.slice(circle,gamePane);

        for (int i = 0; i < lemon.length; i++) {

            if (CIRCLE_RADIUS + LEMON_RADIUS > calculateDistance(circle.getCenterX(), lemon[i].getLayoutX() + 34, circle.getCenterY(), lemon[i].getLayoutY() + 28)) {

                lemon[i].setVisible(false);
                falled[i] = true;
                totalScore += 3;

                lemonSliced1[i].setLayoutX(lemon[i].getLayoutX());
                lemonSliced1[i].setLayoutY(lemon[i].getLayoutY());
                lemonSliced2[i].setLayoutX(lemon[i].getLayoutX());
                lemonSliced2[i].setLayoutY(lemon[i].getLayoutY());

                splash.createParticles(gamePane,(int)lemon[i].getLayoutX()+34,(int)lemon[i].getLayoutY()+28, Color.YELLOW);
                splashEffect.createSplash(gamePane,(int)lemon[i].getLayoutX()-15,(int)lemon[i].getLayoutY()-15);


                lemonSliced1[i].setVisible(true);
                lemonSliced2[i].setVisible(true);

                fixPosition(lemon[i]);

            }

        }

    }

    @Override
    public void stopGame(Boolean isGameStopped) {
        AnimationTimer stop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(isGameStopped)
                    move.stop();
                else move.start();
            }
        };
        stop.start();
    }
}
