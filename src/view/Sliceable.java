package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public interface Sliceable {
    void slice(Circle circle, Pane gamePane);
}
