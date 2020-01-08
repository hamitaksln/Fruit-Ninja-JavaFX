package view;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import view.Bomb;

import java.util.Random;

public class Boom extends Bomb {

    private final static String BOMB_IMAGE = "view/resources/bomb.png";
    private final static int BOMB_RADIUS = 31;




    private ImageView[] bomb;

    Random randomPositionGenerator = new Random();
    private static boolean[] jumping = new boolean[25];
    private static boolean[] falled = new boolean[25];

    BombExplode explosion = new BombExplode();

    public AnimationTimer move;

    public Boom()
    {
        makeTrue();

    }
    private static void makeTrue() {
        for (int i = 0; i < jumping.length; i++) {
            jumping[i] = true;
        }
    }

    @Override
    public void createBomb(Pane gamePane) {

        bomb = new ImageView[25];

        for (int i = 0; i < bomb.length; i++) {
            bomb[i] = new ImageView(BOMB_IMAGE);
            setBombPosition(bomb[i]);
            gamePane.getChildren().add(bomb[i]);
        }
    }


    @Override
    public void setBombPosition(ImageView image) {
        image.setLayoutY(randomPositionGenerator.nextInt((50000 - 900) + 1) + 900);
        image.setLayoutX(randomPositionGenerator.nextInt(200));
    }

    @Override
    public void jumpBomb(ImageView bomb) {
        if (bomb.getLayoutY() < 850) {
            bomb.setLayoutX(bomb.getLayoutX() + 1);

        }

        bomb.setLayoutY(bomb.getLayoutY() - velY);
        bomb.setRotate(bomb.getRotate() + randomPositionGenerator.nextInt((4 - 1) + 1) + 1);

        velY = velY + gravity;
        if (velY > MAX_SPEED) {
            velY = MAX_SPEED;
        }
    }

    @Override
    public void fallBomb(ImageView bomb) {
        bomb.setLayoutX(bomb.getLayoutX() + 1);
        bomb.setLayoutY(bomb.getLayoutY() + velY2);
        bomb.setRotate(bomb.getRotate() + randomPositionGenerator.nextInt((4 - 1) + 1) + 1);

        velY2 = velY2 + gravity;
        if (velY2 > MAX_SPEED) {
            velY2 = MAX_SPEED;
        }
    }

    @Override
    public void moveBomb() {
         move = new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i = 0; i < bomb.length; i++) {
                    if (bomb[i].getLayoutY() > randomPositionGenerator.nextInt((300 - 100) + 1) + 100 & jumping[i] == true) {

                        jumpBomb(bomb[i]);
                    } else if(falled[i] == false){
                        jumping[i] = false;

                        fallBomb(bomb[i]);

                        if (bomb[i].getLayoutY() > 800) {
                            falled[i] = true;
                            fixPosition(bomb[i]);

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
    public void slice(Circle circle,Pane gamePane) {
        super.slice(circle,gamePane);

        for (int i = 0; i < bomb.length; i++) {

            if (CIRCLE_RADIUS + BOMB_RADIUS > calculateDistance(circle.getCenterX(), bomb[i].getLayoutX() + 21, circle.getCenterY(), bomb[i].getLayoutY() + 56)) {

                bomb[i].setVisible(false);
                falled[i] = true;
                isGameOver = true;
                explosion.createBomb(gamePane,bomb[i].getLayoutX()+21,bomb[i].getLayoutY()+56);

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
