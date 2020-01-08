package view.Home;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.FJButtons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Register {
    private final String FONT_PATH = "src/model/resources/Niagaraphobia.otf";
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    public TextField name,surname,username,email;
    public Label lblName,lblSurname,lblUsername,lblEmail,lblPassword;
    public PasswordField password;

    public String sName;
    public String sSurname;
    public String sUsername;
    public String sEmail;
    public String sPassword;

    private FJButtons register;
    private FJButtons login;

    Label warning = new Label();


    //SQL

    Connection connection;

    //public PasswordHash hashPasss;



    public Register()
    {
        register = new FJButtons("REGISTER");
        login = new FJButtons("LOGIN");

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
        mainPane.getChildren().addAll(name,surname,username,email,password,register,login);
        mainPane.getChildren().addAll(lblName,lblSurname,lblUsername,lblEmail,lblPassword,warning);
        elementsAdjustments();

        buttonActions();

    }

    public void buttonActions()
    {


        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sName = name.getText();
                sSurname = surname.getText();
                sUsername = username.getText();
                sEmail = email.getText();
                sPassword = password.getText();

                if(sName.isEmpty() || sSurname.isEmpty() || sUsername.isEmpty() || sEmail.isEmpty() || sPassword.isEmpty())
                {
                    warning.setText("You have to fill all field!");
                    warning.setVisible(true);
                }else

                if(isUsernameUnique())
                {
                    try {
                        sPassword = PasswordHash.generateStrongPasswordHash(sPassword);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }
                    addToDatabase(sName,sSurname,sUsername,sEmail,sPassword);
                    warning.setVisible(true);
                    warning.setText("Register is successful. You will be redirect!");
                    Timeline timeline = new Timeline();

                    KeyFrame kf = new KeyFrame(Duration.millis(2000), e ->{
                        mainStage.close();
                        Login loginScreen = new Login();
                        mainStage = loginScreen.getMainStage();
                        mainStage.show();
                    });
                    timeline.getKeyFrames().add(kf);
                    timeline.setCycleCount(1);
                    timeline.play();

                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else
                {   warning.setText("The username must be unique!");
                    warning.setVisible(true);
                }

            }
        });

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
                Login loginScreen = new Login();
                mainStage = loginScreen.getMainStage();
                mainStage.show();
            }
        });
    }

    public void elementsAdjustments()
    {
        name.setLayoutX(412);name.setLayoutY(300-140);name.setPrefSize(200,50);
        surname.setLayoutX(412);surname.setLayoutY(370-140);surname.setPrefSize(200,50);
        username.setLayoutX(412);username.setLayoutY(440-140);username.setPrefSize(200,50);
        email.setLayoutX(412);email.setLayoutY(510-140);email.setPrefSize(200,50);
        password.setLayoutX(412);password.setLayoutY(580-140);password.setPrefSize(200,50);
        // Labels
        lblName.setLayoutX(320);lblName.setLayoutY(310-140);lblName.setText("Name");lblName.setStyle("-fx-text-fill: black");
        lblSurname.setLayoutX(320);lblSurname.setLayoutY(380-140);lblSurname.setText("Surname");lblSurname.setStyle("-fx-text-fill: black");
        lblUsername.setLayoutX(320);lblUsername.setLayoutY(450-140);lblUsername.setText("Username");lblUsername.setStyle("-fx-text-fill: black");
        lblEmail.setLayoutX(320);lblEmail.setLayoutY(520-140);lblEmail.setText("E-mail");lblEmail.setStyle("-fx-text-fill: black");
        lblPassword.setLayoutX(320);lblPassword.setLayoutY(590-140);lblPassword.setText("Password");lblPassword.setStyle("-fx-text-fill: black");

        warning.setLayoutX(420);warning.setLayoutY(830-140);warning.setText("is cannot be empty !");warning.setVisible(false);warning.setStyle("-fx-text-fill: black");

        //Textfield styles
        name.setStyle("-fx-background-color: sandybrown;-fx-border-color: black;-fx-border-radius: 3;-fx-border-width:2  ");
        surname.setStyle("-fx-background-color: sandybrown;-fx-border-color: black;-fx-border-radius: 3;-fx-border-width:2  ");
        username.setStyle("-fx-background-color: sandybrown;-fx-border-color: black;-fx-border-radius: 3;-fx-border-width:2  ");
        email.setStyle("-fx-background-color: sandybrown;-fx-border-color: black;-fx-border-radius: 3;-fx-border-width:2  ");
        password.setStyle("-fx-background-color: sandybrown;-fx-border-color: black;-fx-border-radius: 3;-fx-border-width:2  ");

        try {
            name.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            surname.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            username.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            email.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            password.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 15));

            lblName.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            lblSurname.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            lblUsername.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            lblEmail.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
            lblPassword.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));

            warning.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));

        } catch (FileNotFoundException e) {
            name.setFont(Font.font("Verdana", 23));
            surname.setFont(Font.font("Verdana", 23));
            username.setFont(Font.font("Verdana", 23));
            email.setFont(Font.font("Verdana", 23));
            password.setFont(Font.font("Verdana", 15));
        }
        register.setLayoutX(412);register.setLayoutY(650-140);
        login.setLayoutX(412);login.setLayoutY(740-140);
    }



    public boolean isUsernameUnique()
    {
        String sql = "SELECT Username as password from player where Username="+"'"+sUsername+"'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next())
            {
              return false;

            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }


    public void createElements()
    {
        name = new TextField();
        surname = new TextField();
        username = new TextField();
        email = new TextField();
        password = new PasswordField();

        //STRINGS

        lblName = new Label();lblName.setText("Name");
        lblSurname = new Label();lblSurname.setText("Surname");
        lblUsername = new Label();lblUsername.setText("Username");
        lblEmail = new Label();lblEmail.setText("Email");
        lblPassword = new Label();lblPassword.setText("Password");
    }


    public void addToDatabase(String name,String surname,String username,String email,String password)
    {
        String sql = "INSERT INTO player (Name, Surname, UserName, Email, Password) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /*public void deneme()
    {
        String sql = "INSERT INTO player (Name, Surname, UserName, Email, Password) VALUES ('Hamit2','Akaslan2','hamitaksln1','hamtaksln@gmail.com','123')";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void createBackground() {
        Image backgroundImage = new Image("view/resources/tile.png", 1024, 768, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    public Stage getMainStage() {
        return mainStage;
    }

}
