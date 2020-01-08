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

public class Banana extends Fruit {

    private final static String BANANA_IMAGE = "view/resources/banana.png";
    private final static String BANANA_SLICED_IMAGE_1 = "view/resources/banana_sliced1.png";
    private final static String BANANA_SLICED_IMAGE_2 = "view/resources/banana_sliced2.png";
    private final static int BANANA_RADIUS = 31;

    private ImageView[] banana;
    private ImageView[] bananaSliced1;
    private ImageView[] bananaSliced2;

    Random randomPositionGenerator;
    private static boolean[] jumping = new boolean[25];
    private static boolean[] falled = new boolean[25];

    public AnimationTimer move;

    Particle splash = new Particle();

    Splash splashEffect = new Splash();

    public Banana()
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

        banana = new ImageView[25];

        for (int i = 0; i < banana.length; i++) {
            banana[i] = new ImageView(BANANA_IMAGE);
            setFruitPosition(banana[i]);
            gamePane.getChildren().add(banana[i]);
        }
    }

    @Override
    public void createSlicedFruit(Pane gamePane) {
        bananaSliced1 = new ImageView[25];
        bananaSliced2 = new ImageView[25];
        for (int i = 0; i < bananaSliced1.length; i++) {
            bananaSliced1[i] = new ImageView(BANANA_SLICED_IMAGE_1);
            setSlicedFruitPosition(bananaSliced1[i], (int) banana[i].getLayoutX(), (int) banana[i].getLayoutY());
            gamePane.getChildren().add(bananaSliced1[i]);
            bananaSliced1[i].setVisible(false);
        }

        for (int i = 0; i < bananaSliced2.length; i++) {
            bananaSliced2[i] = new ImageView(BANANA_SLICED_IMAGE_2);
            setSlicedFruitPosition(bananaSliced2[i], (int) banana[i].getLayoutX() + 36, (int) banana[i].getLayoutY() + 1);
            gamePane.getChildren().add(bananaSliced2[i]);
            bananaSliced2[i].setVisible(false);
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
                for (int i = 0; i < banana.length; i++) {
                    if (banana[i].getLayoutY() > randomPositionGenerator.nextInt((300 - 100) + 1) + 100 & jumping[i] == true) {

                        jumpFruit(banana[i]);
                    } else if(falled[i] == false){
                        jumping[i] = false;

                        fallFruit(banana[i]);

                        if (banana[i].getLayoutY() > 800) {
                            falled[i] = true;
                            life -= 1;
                            fixPosition(banana[i]);

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
        for (int i = 0; i < bananaSliced1.length; i++) {
            bananaSliced1[i].setLayoutY(bananaSliced1[i].getLayoutY() + 3);
            bananaSliced1[i].setRotate(bananaSliced1[i].getRotate() - (randomPositionGenerator.nextInt((5 - 2) + 1) + 2));
            bananaSliced1[i].setLayoutX(bananaSliced1[i].getLayoutX() - 1);

        }
        for (int i = 0; i < bananaSliced2.length; i++) {

            bananaSliced2[i].setLayoutY(bananaSliced2[i].getLayoutY() + 3);
            bananaSliced2[i].setRotate(bananaSliced2[i].getRotate() + randomPositionGenerator.nextInt((5 - 2) + 1) + 2);
            bananaSliced2[i].setLayoutX(bananaSliced2[i].getLayoutX() + 1);

        }
    }
    @Override
    public void slice(Circle circle,Pane gamePane) {
        super.slice(circle,gamePane);

        for (int i = 0; i < banana.length; i++) {

            if (CIRCLE_RADIUS + BANANA_RADIUS > calculateDistance(circle.getCenterX(), banana[i].getLayoutX() + 34, circle.getCenterY(), banana[i].getLayoutY() + 28)) {

                banana[i].setVisible(false);
                falled[i] = true;
                totalScore += 4;

                bananaSliced1[i].setLayoutX(banana[i].getLayoutX());
                bananaSliced1[i].setLayoutY(banana[i].getLayoutY());
                bananaSliced2[i].setLayoutX(banana[i].getLayoutX());
                bananaSliced2[i].setLayoutY(banana[i].getLayoutY());

                splash.createParticles(gamePane,(int)banana[i].getLayoutX()+34,(int)banana[i].getLayoutY()+28, Color.YELLOW);
                splashEffect.createSplash(gamePane,(int)banana[i].getLayoutX()-15,(int)banana[i].getLayoutY()-15);


                bananaSliced1[i].setVisible(true);
                bananaSliced2[i].setVisible(true);

                fixPosition(banana[i]);

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
