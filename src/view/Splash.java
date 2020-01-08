package view;

import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Splash {
    public static final String SPLASH_IMAGE = "view/resources/splash.png";
    public ImageView splash;

    public void createSplash(Pane gamePane,int x,int y)
    {
        splash = new ImageView(SPLASH_IMAGE);


        splash.setLayoutX(x);
        splash.setLayoutY(y);

        FadeTransition fadeTransition =
                new FadeTransition(javafx.util.Duration.millis(700),splash);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);

        fadeTransition.play();

        gamePane.getChildren().add(splash);

    }

}
