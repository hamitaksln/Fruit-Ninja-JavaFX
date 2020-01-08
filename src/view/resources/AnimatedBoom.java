package view.resources;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import view.Bomb;

import java.util.Random;

public class AnimatedBoom extends Bomb {

    private final static String BOMB_IMAGE = "view/resources/bomb.png";
    private final static int BOMB_RADIUS = 31;

    private final static String bomb0 = "view/resources/bomb/0.png";
    private final static String bomb1 = "view/resources/bomb/0.png";
    private final static String bomb2 = "view/resources/bomb/0.png";
    private final static String bomb3 = "view/resources/bomb/0.png";
    private final static String bomb4 = "view/resources/bomb/0.png";

    final ImageView b0 = new ImageView(bomb0);
    final ImageView b1 = new ImageView(bomb1);
    final ImageView b2 = new ImageView(bomb2);
    final ImageView b3 = new ImageView(bomb3);
    final ImageView b4 = new ImageView(bomb4);

    private Group[] aBomb;

    private ImageView[] bomb;

    Random randomPositionGenerator;
    private static boolean[] jumping = new boolean[25];
    private static boolean[] falled = new boolean[25];

    public AnimatedBoom()
    {
        makeTrue();
        aBomb = new Group[25];



        randomPositionGenerator = new Random();
    }
    private static void makeTrue() {
        for (int i = 0; i < jumping.length; i++) {
            jumping[i] = true;
        }
    }

    public void createAnimatedBomb(Pane gamePane)
    {


        for (int i = 0; i <aBomb.length ; i++) {
            aBomb[i] = new Group(b0);
            setAnimatedBomb(aBomb[i]);
            gamePane.getChildren().add(aBomb[i]);
        }
    }
    public void createBombAnimation()
    {
        for(Group boom:aBomb)
        {
            Timeline t = new Timeline();
            t.setCycleCount(Timeline.INDEFINITE);

            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(200),
                    (Ac) -> {
                        boom.getChildren().setAll(b1);
                    }
            ));
            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(300),
                    (Ac) -> {
                        boom.getChildren().setAll(b2);
                    }
            ));
            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(400),
                    (Ac) -> {
                        boom.getChildren().setAll(b3);
                    }
            ));
            t.getKeyFrames().add(new KeyFrame(
                    Duration.millis(500),
                    (Ac) -> {
                        boom.getChildren().setAll(b4);
                    }
            ));



            t.play();
        }






    }


    public void setAnimatedBomb(Group bomb)
    {
        bomb.setLayoutX(randomPositionGenerator.nextInt(100));
        bomb.setLayoutY(200);
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
        AnimationTimer move = new AnimationTimer() {
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
                            life -= 1;
                            System.out.println(life);
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

                System.out.println("GAME OVER");

            }

        }

    }

    @Override
    public void stopGame(Boolean isGameStopped) {

    }
}
