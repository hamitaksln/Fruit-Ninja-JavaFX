package view.Fruits;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import view.Fruit;
import view.Particle;
import view.Splash;

import java.util.Random;

public class Pineapple extends Fruit {

    private final static String PINEAPPLE_IMAGE = "view/resources/pineapple.png";
    private final static String PINEAPPLE_SLICED_IMAGE_1 = "view/resources/pineapple_sliced1.png";
    private final static String PINEAPPLE_SLICED_IMAGE_2 = "view/resources/pineapple_sliced2.png";
    private final static int PINEAPPLE_RADIUS = 26;

    private ImageView[] pineapple;
    private ImageView[] pineappleSliced1;
    private ImageView[] pineappleSliced2;

    Random randomPositionGenerator;
    private static boolean[] jumping = new boolean[25];
    private static boolean[] falled = new boolean[25];

    public AnimationTimer move;

    Particle splash = new Particle();
    Splash splashEffect = new Splash();


    public Pineapple()
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

        pineapple = new ImageView[25];

        for (int i = 0; i < pineapple.length; i++) {
            pineapple[i] = new ImageView(PINEAPPLE_IMAGE);
            setFruitPosition(pineapple[i]);
            gamePane.getChildren().add(pineapple[i]);
        }
    }

    @Override
    public void createSlicedFruit(Pane gamePane) {
        pineappleSliced1 = new ImageView[25];
        pineappleSliced2 = new ImageView[25];
        for (int i = 0; i < pineappleSliced1.length; i++) {
            pineappleSliced1[i] = new ImageView(PINEAPPLE_SLICED_IMAGE_1);
            setSlicedFruitPosition(pineappleSliced1[i], (int) pineapple[i].getLayoutX(), (int) pineapple[i].getLayoutY());
            gamePane.getChildren().add(pineappleSliced1[i]);
            pineappleSliced1[i].setVisible(false);
        }

        for (int i = 0; i < pineappleSliced2.length; i++) {
            pineappleSliced2[i] = new ImageView(PINEAPPLE_SLICED_IMAGE_2);
            setSlicedFruitPosition(pineappleSliced2[i], (int) pineapple[i].getLayoutX() + 36, (int) pineapple[i].getLayoutY() + 1);
            gamePane.getChildren().add(pineappleSliced2[i]);
            pineappleSliced2[i].setVisible(false);
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
                for (int i = 0; i < pineapple.length; i++) {
                    if (pineapple[i].getLayoutY() > randomPositionGenerator.nextInt((300 - 100) + 1) + 100 & jumping[i] == true) {

                        jumpFruit(pineapple[i]);
                    } else if(falled[i] == false){
                        jumping[i] = false;

                        fallFruit(pineapple[i]);

                        if (pineapple[i].getLayoutY() > 800) {
                            falled[i] = true;
                            life -= 1;
                            fixPosition(pineapple[i]);

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
        for (int i = 0; i < pineappleSliced1.length; i++) {
            pineappleSliced1[i].setLayoutY(pineappleSliced1[i].getLayoutY() + 3);
            pineappleSliced1[i].setRotate(pineappleSliced1[i].getRotate() - (randomPositionGenerator.nextInt((5 - 2) + 1) + 2));
            pineappleSliced1[i].setLayoutX(pineappleSliced1[i].getLayoutX() - 1);

        }
        for (int i = 0; i < pineappleSliced2.length; i++) {

            pineappleSliced2[i].setLayoutY(pineappleSliced2[i].getLayoutY() + 3);
            pineappleSliced2[i].setRotate(pineappleSliced2[i].getRotate() + randomPositionGenerator.nextInt((5 - 2) + 1) + 2);
            pineappleSliced2[i].setLayoutX(pineappleSliced2[i].getLayoutX() + 1);

        }
    }
    @Override
    public void slice(Circle circle,Pane gamePane) {
        super.slice(circle,gamePane);

        for (int i = 0; i < pineapple.length; i++) {

            if (CIRCLE_RADIUS + PINEAPPLE_RADIUS > calculateDistance(circle.getCenterX(), pineapple[i].getLayoutX() + 29, circle.getCenterY(), pineapple[i].getLayoutY() + 85)) {

                pineapple[i].setVisible(false);
                falled[i] = true;
                totalScore += 2;

                pineappleSliced1[i].setLayoutX(pineapple[i].getLayoutX());
                pineappleSliced1[i].setLayoutY(pineapple[i].getLayoutY());
                pineappleSliced2[i].setLayoutX(pineapple[i].getLayoutX());
                pineappleSliced2[i].setLayoutY(pineapple[i].getLayoutY());

                splash.createParticles(gamePane,(int)pineapple[i].getLayoutX()+34,(int)pineapple[i].getLayoutY()+28, Color.GOLD);
                splashEffect.createSplash(gamePane,(int)pineapple[i].getLayoutX()-15,(int)pineapple[i].getLayoutY()-15);


                pineappleSliced1[i].setVisible(true);
                pineappleSliced2[i].setVisible(true);

                fixPosition(pineapple[i]);

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
