package view;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BombExplode {
    public static final String BOMB_IMAGES = "view/resources/explosion.png";
    public ImageView bombEffect;
    public Timeline timeline;
    public void createBomb(Pane gamePane,double x,double y)
    {
        final int numberOfFrames = 64;

        Image boom = new Image(BOMB_IMAGES,32768,512,false,false);
        bombEffect = new ImageView(boom);
        bombEffect.setLayoutX(x-256);
        bombEffect.setLayoutY(y-256);
        double frameWidth = boom.getWidth() / numberOfFrames;
        Rectangle mask = new Rectangle(512,512);

        bombEffect.setClip(mask);
        int mSecond = 500;
        timeline = new Timeline();

        for (int j = 0; j <=numberOfFrames ; j++) {
            mSecond +=50;
            KeyFrame kf = new KeyFrame(Duration.millis(mSecond), new KeyValue(bombEffect.xProperty(), -frameWidth * j, Interpolator.DISCRETE));
            timeline.getKeyFrames().add(kf);
        }
        timeline.setCycleCount(1);
        timeline.play();
        gamePane.getChildren().add(bombEffect);
    }
}
