package view.Home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.FJButtons;
import view.ViewManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login {
    private final String FONT_PATH = "src/model/resources/Niagaraphobia.otf";
    public static String currentUsername;
    public static String currentPassword;
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    private FJButtons login;
    private FJButtons register;

    Label warning = new Label();
    TextField username = new TextField();
    PasswordField password = new PasswordField();
    String sUsername; String sPassword;
    Label lbUsername = new Label();
    Label lbPassword = new Label();

    Connection connection;

    public Login()
    {
        login = new FJButtons("LOGIN");
        register = new FJButtons("REGISTER");
        connection = ConnectionUtil.connectionDB();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.setTitle("Fruit Janissary");
        mainStage.getIcons().add(new Image("view/resources/watermelon.png"));
        createBackground();
        createElements();
        buttonActions();
    }

    public void createElements()
    {

        username.setLayoutX(412);username.setLayoutY(300);username.setPrefSize(200,50);
        password.setLayoutX(412);password.setLayoutY(370);password.setPrefSize(200,50);
        login.setLayoutX(412);login.setLayoutY(440);
        register.setLayoutX(412);register.setLayoutY(530);
        //Labels
        warning.setLayoutX(390);warning.setLayoutY(650);warning.setText("Incorrect username or password");warning.setVisible(false);warning.setStyle("-fx-text-fill: black");
        lbUsername.setLayoutX(320);lbUsername.setLayoutY(310);lbUsername.setText("Username");lbUsername.setStyle("-fx-text-fill: black");
        lbPassword.setLayoutX(320);lbPassword.setLayoutY(380);lbPassword.setText("Password");lbPassword.setStyle("-fx-text-fill: black");
        //Textfield styles
        username.setStyle("-fx-background-color: sandybrown;-fx-border-color: black;-fx-border-radius: 3;-fx-border-width:2  ");
        password.setStyle("-fx-background-color: sandybrown;-fx-border-color: black;-fx-border-radius: 3;-fx-border-width:2  ");

        try {
            username.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            lbUsername.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            lbPassword.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            password.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 15));
            warning.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));

        } catch (FileNotFoundException e) {
            username.setFont(Font.font("Verdana", 23));
            lbUsername.setFont(Font.font("Verdana", 23));
            lbPassword.setFont(Font.font("Verdana", 23));
            password.setFont(Font.font("Verdana", 15));
            warning.setFont(Font.font("Verdana", 23));
        }
        //Create Logo

        ImageView logo = new ImageView("view/resources/logo.png");
        logo.setLayoutX(170);
        logo.setLayoutY(40);

        mainPane.getChildren().addAll(username,password,login,register,warning,lbUsername,lbPassword,logo);


    }

    public void buttonActions()
    {
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
                Register registerScreen = new Register();
                mainStage = registerScreen.getMainStage();
                mainStage.show();
            }
        });


        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                sUsername = username.getText();
                sPassword = password.getText();
                getDatas(sUsername);


                try {
                    if(PasswordHash.validatePassword(sPassword,currentPassword))
                    {
                        currentUsername = sUsername;
                        mainStage.close();
                        ViewManager manager = new ViewManager();
                        mainStage = manager.getMainStage();
                        mainStage.show();
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    else warning.setVisible(true);
                } catch (Exception e) {
                    warning.setVisible(true);
                }
            }
        });
    }

    public void getDatas(String username)
    {
        String sql = "SELECT Password as password from player where Username="+"'"+sUsername+"'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                currentPassword = rs.getString("password");


            }
        } catch (Exception e) {
            warning.setVisible(true);

        }
    }

    public void createBackground() {
        Image backgroundImage = new Image("view/resources/tile.png", 1024, 768, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }


    public Stage getMainStage() {
        return mainStage;
    }
}
