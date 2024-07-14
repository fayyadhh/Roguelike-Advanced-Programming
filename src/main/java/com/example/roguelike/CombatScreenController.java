package com.example.roguelike;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class CombatScreenController {
    public CombatScreen cs;
    public CombatScreenController csc;
    public Player player;
    public Enemy enemy;
    @FXML
    private Button attackButton;
    @FXML
    private Button itemsButton;
    @FXML
    private Button runButton;
    @FXML
    private Button testButton;
    @FXML
    private Button endTestButton;
    @FXML
    private Button defeatTest;

    //QTE Elements
    @FXML
    private StackPane qtePane;


    //variables
    private Timeline qteTimeline;
    private double qtePosition;

    //Variables for qte zones
    private Rectangle greenZone;
    private Rectangle yellowZone;
    private Rectangle qteBar;

    public void initialize(){
        cs = new CombatScreen();


        attackButton.setOnAction(event -> handleAttack());
        itemsButton.setOnAction(event -> showItems());
        runButton.setOnAction(event -> escapeEncounter());

        //Quick Time Event Thingy
        qteBar = new Rectangle(5,100);
        qteBar.setFill(Color.RED);
        greenZone = new Rectangle();
        greenZone.setFill(Color.GREEN);
        yellowZone = new Rectangle();
        yellowZone.setFill(Color.YELLOW);

        qtePane.getChildren().addAll(greenZone, yellowZone, qteBar);
        qtePane.setStyle("-fx-background-color: black;");
        qtePane.setVisible(false);


        //Test Buttons
        testButton.setOnAction(event -> afterCombat());
        endTestButton.setOnAction(event -> winGame());
        defeatTest.setOnAction(event -> defeated());
    }

    private void handleAttack(){
        //Show the QTE and begin the Quick Time Event for combat
        qtePane.setVisible(true);

        //Set the other buttons as invisible
        attackButton.setVisible(false);
        itemsButton.setVisible(false);
        runButton.setVisible(false);
        startQTE();
    }

    private void startQTE(){
        Random rand = new Random();
        double totalWidth = qtePane.getWidth();
        double critWidth = totalWidth * 0.1;
        double regularHitWidth = totalWidth * 0.2; //Just the size for the regular hit. change the mult if needed

        //Randomize green and yellow positions
        double greenStart = rand.nextDouble() * (totalWidth - critWidth);
        double yellowStart;
        do{
            yellowStart = rand.nextDouble() * (totalWidth - regularHitWidth);
        } while (Math.abs(yellowStart - greenStart) < critWidth); //Ensure the zones don't overlap

        //Make sure that thezones stay within the bounds PLEASE PLEASE PLEASE STAY WITHIN THE BOUNDS I BEG YOU PLEASE
        greenStart = Math.max(0, Math.min(greenStart, totalWidth - critWidth));
        yellowStart = Math.max(0, Math.min(yellowStart, totalWidth - regularHitWidth));

        greenZone.setWidth(critWidth);
        greenZone.setHeight(qtePane.getHeight());
        greenZone.setTranslateX(greenStart);

        yellowZone.setWidth(regularHitWidth);
        yellowZone.setHeight(qtePane.getHeight());
        yellowZone.setTranslateX(yellowStart);

        qtePosition = -qteBar.getWidth() - 250;
        qteBar.setTranslateX(qtePosition);

        //QTE Timeline stuff
        qteTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> moveQTE()),
                new KeyFrame(Duration.millis(20))
        );
        //Play the QTE
        qteTimeline.setCycleCount((int) ((totalWidth + qteBar.getWidth())) / 5);
        qteTimeline.setOnFinished(e -> endQTE());
        qteTimeline.play();

        qtePane.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleQTEClick);
    }

    private void moveQTE(){
        qtePosition += 5.0;
        qteBar.setTranslateX(qtePosition);
    }

    private void handleQTEClick(MouseEvent event){
        qteTimeline.stop();
        qtePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this::handleQTEClick);

        checkQTEHit();
    }

    private void checkQTEHit(){
        double barCenter = qteBar.getTranslateX() + qteBar.getWidth() / 2;
        double greenStart = greenZone.getTranslateX();
        double greenEnd = greenStart + greenZone.getWidth();

        double yellowStart = yellowZone.getTranslateX();
        double yellowEnd = yellowStart + yellowZone.getWidth();

        int damage;

        if(barCenter >= greenStart && barCenter <= greenEnd){
            //Crit Damage
            damage = 50; //For now setting this to 50, but i'll change so that it does crit damage.

        } else if (barCenter >= yellowStart && barCenter <= yellowEnd){
            //Regular Hit
            damage = 20; //same as above. change later
        } else{
            damage = 0; //Miss lol
        }

        attackEnemy(damage);
        qtePane.setVisible(false);

        attackButton.setVisible(true);
        itemsButton.setVisible(true);
        runButton.setVisible(true);
    }

    private void endQTE(){
        qtePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this::handleQTEClick);
        checkQTEHit();
    }

    public void defeated() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DefeatedScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) attackButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void winGame() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VictoryScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) attackButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showItems() {
    }

    private void escapeEncounter() {
    }

    public void attackEnemy(int damage) {
        cs.Attacked(damage);
    }

    public void afterCombat(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RewardsScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) attackButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
