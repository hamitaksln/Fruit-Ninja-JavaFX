package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FJButtons extends Button {

    private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-repeat: no-repeat; -fx-background-image: url('/model/resources/button_pressed.png')";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-repeat: no-repeat; -fx-background-image: url('/model/resources/button_released.png');";
    //private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/blue_button.png');";
    //private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/blue_button_pressed.png');";
    Color color = Color.BLACK;
    public FJButtons(String text) {
        setText(text);
        color.darker();
        setTextFill(Color.SADDLEBROWN.darker());
        setButtonFont();
        setPrefWidth(200);
        setPrefHeight(74);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListeners();
    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }

    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        //setLayoutY(getLayoutY() + 4);
        setPrefHeight(78);
    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
       // setLayoutY(getLayoutY() - 4);
        setPrefHeight(74);
    }

    private void initializeButtonListeners() {
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }
            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }
            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }

}
