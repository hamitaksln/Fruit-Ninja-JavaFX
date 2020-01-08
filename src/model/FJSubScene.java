package model;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class FJSubScene extends SubScene {

    private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private final String BACKGROUND_IMAGE = "model/resources/panel.png";

    private boolean isHidden = true;
    public FJSubScene() {
        super(new AnchorPane(),650,450);
        prefWidth(650);
        prefHeight(450);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE,650,450,false,false),
                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);
        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));
        setLayoutX(1050);
        setLayoutY(170);
    }

    public void moveSubScene()
    {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if(isHidden){
            transition.setToX(-700);
            isHidden = false;
        }else{
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }

    public AnchorPane getPane()
    {
        return (AnchorPane) this.getRoot();
    }
}
