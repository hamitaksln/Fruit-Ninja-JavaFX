package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.FJButtons;
import model.FJSubScene;
import view.Home.ConnectionUtil;
import view.Home.Login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;


public class ViewManager {

    private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private static final int HEIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    public Scene mainScene;
    public Stage mainStage;

    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 150;

    //Fruit images for Help
    private final static String WATERMELON_IMAGE = "view/resources/watermelon.png";
    private final static String LEMON_IMAGE = "view/resources/lemon.png";
    private final static String BANANA_IMAGE = "view/resources/banana.png";
    private final static String PINEAPPLE_IMAGE = "view/resources/pineapple.png";

    private FJSubScene creditsSubScene;
    private FJSubScene helpSubScene;
    private FJSubScene scoreSubScene;
    private FJSubScene ownScoresSubScene;
    private FJSubScene startGameScene;

    private FJSubScene sceneToHide;

    Text topScoreText = new Text();
    Text topScoreTextforOwnSubScene = new Text();
    Text scoreFirst = new Text();
    Text scoreSecond = new Text();
    Text scoreThird = new Text();
    Text scoreFourth = new Text();
    Text scoreFifth = new Text();
    Text duration = new Text();
    Text date = new Text();

    Text hamit = new Text();
    Text emrah = new Text();
    Text mehmet = new Text();

    Text watermelonText = new Text("2 POINTS");
    Text lemonText  = new Text("3 POINTS");
    Text bananaText  = new Text("4 POINTS");
    Text pineappleText  = new Text("2 POINTS");

    //Text[] usernameScore = new Text[5];
    ArrayList<Text> highScoresUsernameText = new ArrayList<>();
    ArrayList<Text> highScoresScoresText = new ArrayList<>();
    ArrayList<Text> highScoresDurationsText = new ArrayList<>();
    ArrayList<Text> highScoresDateText = new ArrayList<>();


    ArrayList<Text> ownScoresText = new ArrayList<>();
    ArrayList<Text> ownDurationsText = new ArrayList<>();
    ArrayList<Text> ownDateText = new ArrayList<>();


    ArrayList<String> data = new ArrayList<>();
    ArrayList<String> scoreList = new ArrayList<>();

    private ImageView watermelon;
    private ImageView lemon;
    private ImageView banana;
    private ImageView pineapple;


    Connection connection;
    Login currentUsername = new Login();

    DateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    Date date1 = new Date();


    List<FJButtons> menuButtons;



    public ViewManager() {
        connection = ConnectionUtil.connectionDB();
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.setTitle("Fruit Janissary");
        mainStage.getIcons().add(new Image("view/resources/watermelon.png"));
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();

    }

    private void showSubScene(FJSubScene subScene)
    {
        if(sceneToHide != null)
        {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScenes()
    {

        creditsSubScene = new FJSubScene();
        mainPane.getChildren().add(creditsSubScene);
        creditsSubScene.getPane().getChildren().addAll(hamit,emrah,mehmet);
        createCreditsTexts();

        helpSubScene = new FJSubScene();
        mainPane.getChildren().add(helpSubScene);
        createHelpImages();

        scoreSubScene = new FJSubScene();
        mainPane.getChildren().add(scoreSubScene);
        scoreSubScene.getPane().getChildren().addAll(scoreFirst,scoreSecond,scoreThird,scoreFourth,scoreFifth,duration,date,topScoreText);

        ownScoresSubScene = new FJSubScene();
        mainPane.getChildren().add(ownScoresSubScene);
        FJButtons ownScoresButton = new FJButtons("Own Scores");
        scoreSubScene.getPane().getChildren().add(ownScoresButton);
        ownScoresSubScene.getPane().getChildren().add(topScoreTextforOwnSubScene);

        ownScoresButton.setLayoutX(212);
        ownScoresButton.setLayoutY(340);

        setHighScores();
        showScoresinSubscene();
        setOwnScores();
        showHighScoresinHighScoreSubScene();
        showOwnScoresinOwnSubScene();

        ownScoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                showSubScene(ownScoresSubScene);
            }
        });
        createStartGameSubScene();

    }
    private void createHelpImages()
    {
        watermelon = new ImageView(WATERMELON_IMAGE);
        lemon = new ImageView(LEMON_IMAGE);
        banana = new ImageView(BANANA_IMAGE);
        pineapple = new ImageView(PINEAPPLE_IMAGE);

        watermelon.setLayoutX(150);watermelon.setLayoutY(20);
        lemon.setLayoutX(150);lemon.setLayoutY(100);
        banana.setLayoutX(150);banana.setLayoutY(180);
        pineapple.setLayoutX(150);pineapple.setLayoutY(260);

        watermelonText.setLayoutX(300);watermelonText.setLayoutY(20+40);
        lemonText.setLayoutX(300);lemonText.setLayoutY(100+40);
        bananaText.setLayoutX(300);bananaText.setLayoutY(180+50);
        pineappleText.setLayoutX(300);pineappleText.setLayoutY(260+80);

        try {
            watermelonText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
            lemonText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
            bananaText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
            pineappleText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 30));
        } catch (FileNotFoundException e) {
            watermelonText.setFont(Font.font("Verdana", 30));
            lemonText.setFont(Font.font("Verdana", 30));
            bananaText.setFont(Font.font("Verdana", 30));
            pineappleText.setFont(Font.font("Verdana", 30));
        }

        helpSubScene.getPane().getChildren().addAll(watermelon,lemon,banana,pineapple);
        helpSubScene.getPane().getChildren().addAll(watermelonText,lemonText,bananaText,pineappleText);


    }
    private void createCreditsTexts() {

        hamit.setText("Abdulhamit Akaslan");hamit.setLayoutX(80);hamit.setLayoutY(100);
        emrah.setText("Emrah Batıgün");emrah.setLayoutX(80);emrah.setLayoutY(200);
        mehmet.setText("Mehmet Yigit");mehmet.setLayoutX(80);mehmet.setLayoutY(300);

        try {
            hamit.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 50));
            emrah.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 50));
            mehmet.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 50));
        } catch (FileNotFoundException e) {
            hamit.setFont(Font.font("Verdana", 50));
            emrah.setFont(Font.font("Verdana", 50));
            mehmet.setFont(Font.font("Verdana", 50));
        }


    }

    private void showScoresinSubscene()
    {
        scoreFirst.setLayoutX(100);scoreFirst.setLayoutY(100);
        scoreSecond.setLayoutX(100);scoreSecond.setLayoutY(150);
        scoreThird.setLayoutX(100);scoreThird.setLayoutY(200);
        scoreFourth.setLayoutX(100);scoreFourth.setLayoutY(250);
        scoreFifth.setLayoutX(100);scoreFifth.setLayoutY(300);

        duration.setLayoutX(100);duration.setLayoutY(50);
        date.setLayoutX(150);date.setLayoutY(50);

        try {
            topScoreText.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 25));
            topScoreTextforOwnSubScene.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 25));
            scoreFirst.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreSecond.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreThird.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreFourth.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
            scoreFifth.setFont(javafx.scene.text.Font.loadFont(new FileInputStream(FONT_PATH), 20));
        } catch (FileNotFoundException e) {
            topScoreText.setFont(Font.font("Verdana", 25));
            topScoreTextforOwnSubScene.setFont(Font.font("Verdana", 25));
            scoreFirst.setFont(Font.font("Verdana", 20));
            scoreSecond.setFont(Font.font("Verdana", 20));
            scoreThird.setFont(Font.font("Verdana", 20));
            scoreFourth.setFont(Font.font("Verdana", 20));
            scoreFifth.setFont(Font.font("Verdana", 20));
        }
        topScoreText.setText("USERNAME     SCORE     DURATION       DATE");
        topScoreText.setLayoutX(40);
        topScoreText.setLayoutY(45);
        topScoreText.setUnderline(true);
        topScoreText.setFill(Color.ORANGE);

        topScoreTextforOwnSubScene.setText("SCORE          DURATION            DATE");
        topScoreTextforOwnSubScene.setLayoutX(115);
        topScoreTextforOwnSubScene.setLayoutY(45);
        topScoreTextforOwnSubScene.setUnderline(true);
        topScoreTextforOwnSubScene.setFill(Color.ORANGE);




    }

    private void showHighScoresinHighScoreSubScene()
    {
        for (int i = 0; i <highScoresUsernameText.size() ; i++) {
            scoreSubScene.getPane().getChildren().add(highScoresUsernameText.get(i));
            try {
                highScoresUsernameText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
            } catch (FileNotFoundException e) {
                highScoresUsernameText.get(i).setFont(Font.font("Verdana", 20));

            }
            highScoresUsernameText.get(i).setLayoutX(70);
            highScoresUsernameText.get(i).setLayoutY(100+(i*50));
        }
    }

    private void showOwnScoresinOwnSubScene()
    {
        for (int i = 0; i <ownScoresText.size() ; i++) {
            ownScoresSubScene.getPane().getChildren().add(ownScoresText.get(i));
            ownScoresSubScene.getPane().getChildren().add(ownDurationsText.get(i));
            ownScoresSubScene.getPane().getChildren().add(ownDateText.get(i));

            try {

                ownScoresText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
                ownDurationsText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
                ownDateText.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
            } catch (FileNotFoundException e) {
                ownScoresText.get(i).setFont(Font.font("Verdana", 20));
                ownDurationsText.get(i).setFont(Font.font("Verdana", 20));
                ownDateText.get(i).setFont(Font.font("Verdana", 20));

            }
            ownScoresText.get(i).setLayoutX(135);ownScoresText.get(i).setLayoutY(100+(i*50));
            ownDurationsText.get(i).setLayoutX(280);ownDurationsText.get(i).setLayoutY(100+(i*50));
            ownDateText.get(i).setLayoutX(395);ownDateText.get(i).setLayoutY(100+(i*50));

            //System.out.println(ownScoresText.get(i).getText());
        }
    }

    private void setHighScores()
    {
        String sql = "SELECT Scores as score,Durations as duration ,Date as nowdate,Username as username  from gameinfo order by Scores desc limit 5";
        int i = 0;

        try {


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                String scoree = rs.getString("score");
                String duration = rs.getString("duration");
                String date = rs.getString("nowdate");
                String username = rs.getString("username");
                highScoresScoresText.add(new Text(scoree));
                highScoresDurationsText.add(new Text(duration));
                highScoresDateText.add(new Text(date));
                highScoresUsernameText.add(new Text(username));

                data.add("                          "+scoree+"                    "+duration+"              "+date);
                i++;
            }
            String score1,score2,score3,score4,score5;
            score1 = data.get(0);
            score2 = data.get(1);
            score3 = data.get(2);
            score4 = data.get(3);
            score5 = data.get(4);
            scoreFirst.setText(score1);
            scoreSecond.setText(score2);
            scoreThird.setText(score3);
            scoreFourth.setText(score4);
            scoreFifth.setText(score5);
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setOwnScores()
    {
        String sqlUsername = "'"+currentUsername.currentUsername+"'";
        String sql = "SELECT Scores as score,Durations as duration ,Date as nowdate,Username as username  from gameinfo where Username="+sqlUsername+" order by Scores desc limit 5";
        int i = 0;

        try {


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                String scoree = rs.getString("score");
                String duration = rs.getString("duration");
                String date = rs.getString("nowdate");
                String username = rs.getString("username");
                ownScoresText.add(new Text(scoree));
                ownDurationsText.add(new Text(duration));
                ownDateText.add(new Text(date));
                //usernameScore[i] = new Text(username);
                //String all = usernameScore[i].getText()+" "+ownScoresText.get(i).getText()+" "+ownDurationsText.get(i).getText()+" "+ownDateText.get(i).getText();
                //System.out.println(all);
                //System.out.println(ownScoresText.get(i).getText());
                //System.out.println();
                //data.add("                          "+scoree+"                    "+duration+"              "+date);
                i++;
            }
            String score1,score2,score3,score4,score5;
            score1 = data.get(0);
            score2 = data.get(1);
            score3 = data.get(2);
            score4 = data.get(3);
            score5 = data.get(4);
            scoreFirst.setText(score1);
            scoreSecond.setText(score2);
            scoreThird.setText(score3);
            scoreFourth.setText(score4);
            scoreFifth.setText(score5);
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createStartGameSubScene() {
        startGameScene = new FJSubScene();
        mainPane.getChildren().add(startGameScene);

        Button button1 = new Button();
        button1.setPrefHeight(50);
        button1.setPrefWidth(100);
        button1.setLayoutX(100);
        button1.setLayoutY(100);


        startGameScene.getPane().getChildren().add(button1);

    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(FJButtons button)
    {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size()*100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();

    }

    private void createStartButton()
    {
        FJButtons startButton = new FJButtons("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newGame();
            }
        });
    }

    public void newGame()
    {   mainStage.close();
        GameViewManager gameManager = new GameViewManager();
        gameManager.createNewGame(mainStage);
    }

    private void createScoresButton()
    {
        FJButtons scoresButton = new FJButtons("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoreSubScene);

                //showHighScoresinHighScoreSubScene();
            }
        });
    }

    private void createHelpButton()
    {
        FJButtons helpButton = new FJButtons("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton()
    {
        FJButtons creditsButton = new FJButtons("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton()
    {
        FJButtons exitButton = new FJButtons("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("view/resources/tile.png", 1024, 768, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void createLogo()
    {
        ImageView logo = new ImageView("view/resources/logo.png");
        logo.setLayoutX(330);
        logo.setLayoutY(40);


        mainPane.getChildren().add(logo);
    }
}
