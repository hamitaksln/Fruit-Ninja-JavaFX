package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Home.Login;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //ViewManager manager = new ViewManager();
        //primaryStage = manager.getMainStage();

        Login loginScreen = new Login();
        primaryStage = loginScreen.getMainStage();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
