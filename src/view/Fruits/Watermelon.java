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

public class Watermelon extends Fruit {

    private final static String WATERMELON_IMAGE = "view/resources/watermelon.png";
    private final static String WATERMELON_SLICED_IMAGE_1 = "view/resources/watermelon_sliced1.png";
    private final static String WATERMELON_SLICED_IMAGE_2 = "view/resources/watermelon_sliced2.png";
    private final static int WATERMELON_RADIUS = 34;

    private ImageView[] watermelon;
    private ImageView[] watermelonSliced1;
    private ImageView[] watermelonSliced2;

    Random randomPositionGenerator;
    private static boolean[] jumping = new boolean[25];
    private static boolean[] falled = new boolean[25];

    public AnimationTimer move;

    Particle splash = new Particle();

    Splash splashEffect = new Splash();

    public Watermelon() {
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

        watermelon = new ImageView[25];

        for (int i = 0; i < watermelon.length; i++) {
            watermelon[i] = new ImageView(WATERMELON_IMAGE);
            setFruitPosition(watermelon[i]);
            gamePane.getChildren().add(watermelon[i]);
        }
    }

    @Override
    public void createSlicedFruit(Pane gamePane) {
        watermelonSliced1 = new ImageView[25];
        watermelonSliced2 = new ImageView[25];
        for (int i = 0; i < watermelonSliced1.length; i++) {
            watermelonSliced1[i] = new ImageView(WATERMELON_SLICED_IMAGE_1);
            setSlicedFruitPosition(watermelonSliced1[i], (int) watermelon[i].getLayoutX(), (int) watermelon[i].getLayoutY());
            gamePane.getChildren().add(watermelonSliced1[i]);
            watermelonSliced1[i].setVisible(false);
        }

        for (int i = 0; i < watermelonSliced2.length; i++) {
            watermelonSliced2[i] = new ImageView(WATERMELON_SLICED_IMAGE_2);
            setSlicedFruitPosition(watermelonSliced2[i], (int) watermelon[i].getLayoutX() + 36, (int) watermelon[i].getLayoutY() + 1);
            gamePane.getChildren().add(watermelonSliced2[i]);
            watermelonSliced2[i].setVisible(false);
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
                for (int i = 0; i < watermelon.length; i++) {
                    if (watermelon[i].getLayoutY() > randomPositionGenerator.nextInt((300 - 100) + 1) + 100 & jumping[i] == true) {

                        jumpFruit(watermelon[i]);
                    } else if(falled[i] == false){
                        jumping[i] = false;

                        fallFruit(watermelon[i]);

                        if (watermelon[i].getLayoutY() > 800) {
                            falled[i] = true;
                            life -= 1;
                            fixPosition(watermelon[i]);

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
        for (int i = 0; i < watermelonSliced1.length; i++) {
            watermelonSliced1[i].setLayoutY(watermelonSliced1[i].getLayoutY() + 3);
            watermelonSliced1[i].setRotate(watermelonSliced1[i].getRotate() - (randomPositionGenerator.nextInt((4 - 2) + 1) + 2));
            watermelonSliced1[i].setLayoutX(watermelonSliced1[i].getLayoutX() - 1);

        }
        for (int i = 0; i < watermelonSliced2.length; i++) {

            watermelonSliced2[i].setLayoutY(watermelonSliced2[i].getLayoutY() + 3);
            watermelonSliced2[i].setRotate(watermelonSliced2[i].getRotate() + randomPositionGenerator.nextInt((5 - 2) + 1) + 2);
            watermelonSliced2[i].setLayoutX(watermelonSliced2[i].getLayoutX() + 1);

        }
    }

    @Override
    public void slice(Circle circle,Pane gamePane) {
        super.slice(circle,gamePane);

        for (int i = 0; i < watermelon.length; i++) {

            if (CIRCLE_RADIUS + WATERMELON_RADIUS > calculateDistance(circle.getCenterX(), watermelon[i].getLayoutX() + 34, circle.getCenterY(), watermelon[i].getLayoutY() + 28)) {

                watermelon[i].setVisible(false);
                falled[i] = true;
                totalScore += 2;

                watermelonSliced1[i].setLayoutX(watermelon[i].getLayoutX());
                watermelonSliced1[i].setLayoutY(watermelon[i].getLayoutY());
                watermelonSliced2[i].setLayoutX(watermelon[i].getLayoutX());
                watermelonSliced2[i].setLayoutY(watermelon[i].getLayoutY());

                splash.createParticles(gamePane,(int)watermelon[i].getLayoutX()+34,(int)watermelon[i].getLayoutY()+28,Color.RED);
                splashEffect.createSplash(gamePane,(int)watermelon[i].getLayoutX()-15,(int)watermelon[i].getLayoutY()-15);
                watermelonSliced1[i].setVisible(true);
                watermelonSliced2[i].setVisible(true);

                fixPosition(watermelon[i]);

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
