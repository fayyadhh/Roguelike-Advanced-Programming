package com.example.roguelike;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class CombatScreenController {
    //private CombatScreen cs;
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

    //Player Elements
    @FXML
    private ProgressBar playerHealthBar;
    @FXML
    private ImageView playerImage;
    @FXML
    private Text playerHitIndicator;

    //Enemy elements
    @FXML //@FML haha lol 
    private ProgressBar enemyHealthBar;
    @FXML
    private Text enemyName;
    @FXML
    private Text enemyHitIndicator;


    //variables
    private Player player;
    private Enemy enemy = new Enemy();
    private Level level;
    private Timeline qteTimeline;
    private double qtePosition;

    //Variables for qte zones
    private Rectangle greenZone;
    private Rectangle yellowZone;
    private Rectangle qteBar;

    @FXML
    public void initialize(){
        //cs = new CombatScreen();
        
        //Level Initialization
        level = new Level();
        
        //Spawns the Enemy
        spawnEnemy();
        enemyHitIndicator.setVisible(false);
        enemyName.setVisible(false);

        //TODO Conditional Statement to switch the enemy sprite based on the enemy that spawns


        attackButton.setOnAction(event -> handleAttack());
        itemsButton.setOnAction(event -> showItems());
        runButton.setOnAction(event -> escapeEncounter());

        //Player Linking
        player = new Player("Player", 100, 1, 0);
        playerHealthBar.setProgress(player.getCurrentHealth() / player.getMaxHealth());
        playerHitIndicator.setVisible(false);


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

    private void updatePlayerGUI(){
        //Updates player health on the progress bar
        if (player != null){
            playerHealthBar.setProgress((double) player.getCurrentHealth() / player.getMaxHealth());
        }
    }

    private void spawnEnemy(){
        //Checks the level and spawns the appropriate enemy
        int currentLevel = level.getLevel() % 10;
        switch (currentLevel){
            case 5:
                enemy = enemy.spawnRandomEnemy(EnemyType.STRONG);
                break;
            case 10:
                enemy = enemy.spawnRandomBoss();
                break;
            default:
                enemy = enemy.spawnRandomEnemy(EnemyType.REGULAR);
                break;
        }
        updateEnemyGUI();    
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
        double critWidth = totalWidth * player.getCritMultiplier();
        double regularHitWidth = totalWidth * 0.2; //Just the size for the regular hit. change the mult if needed

        //Randomize green and yellow positions
        double greenStart = rand.nextDouble() * (totalWidth - critWidth);
        double yellowStart;
        do{
            yellowStart = rand.nextDouble() * (totalWidth - regularHitWidth);
        } while (Math.abs(yellowStart - greenStart) < critWidth + regularHitWidth && yellowStart < greenStart + critWidth); //Ensure the zones don't overlap

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

    private void endQTE(){
            qtePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this::handleQTEClick);
            checkQTEHit();
    }
        
    private void checkQTEHit(){
    double barStart = qteBar.getTranslateX();
    double barEnd = barStart + qteBar.getWidth();

    double barCenter = qteBar.getTranslateX() + qteBar.getWidth() / 2;
    double greenStart = greenZone.getTranslateX();
    double greenEnd = greenStart + greenZone.getWidth();

    double yellowStart = yellowZone.getTranslateX();
    double yellowEnd = yellowStart + yellowZone.getWidth();

    double attackMultiplier;

    if(barStart <= greenEnd && barEnd >= greenStart){
        //Crit Damage
        attackMultiplier = 1.5; //For now setting this to 50, but i'll change so that it does crit damage.
        playerHitIndicator.setText("CRIT! Dealt " + (int) (player.getAttackPower() * attackMultiplier) + " damage!");

    } else if (barStart <= yellowEnd && barEnd >= yellowStart){
        //Regular Hit
        attackMultiplier = 1; //same as above. change later
        playerHitIndicator.setText("HIT! Dealt " + (int) (player.getAttackPower() * attackMultiplier) + " damage!");
    } else{
        attackMultiplier = 0; //Miss lol
        playerHitIndicator.setText("MISS! Better Luck Next Time");
    }

    attackEnemy(attackMultiplier);
     
    qtePane.setVisible(false);

    playerHitIndicator.setVisible(true);

    attackButton.setVisible(true);
    itemsButton.setVisible(true);
    runButton.setVisible(true);

    enemyAttack();
    }

    

    private void defeated() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DefeatScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) attackButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void winGame() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("VictoryScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) attackButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showItems() {
    }

    private void escapeEncounter() {
    }

    private void enemyAttack() {
        String enemyAction = enemy.enemyTurn();
        enemyHitUpdate(enemyAction);
        updatePlayerGUI();
        
        if(player.checkPlayerDead()){
            defeated();
        }
    }

    private void enemyHitUpdate(String action) {
        switch (action){
            case "Attack":
                enemyHitIndicator.setText("Enemy Attacked for " + enemy.getAttackPower());
                enemyHitIndicator.setVisible(true);
                break;
            case "Block":
                enemyHitIndicator.setText("Enemy Blocked");
                enemyHitIndicator.setVisible(true);
                break;
        }
    }

    private void attackEnemy(double attackMultiplier) {
        // cs.Attacked(damage);

        // if(cs.isEnemyDead()){
        //     afterCombat();
        // }

        player.attack(enemy, attackMultiplier);

        updateEnemyGUI();

        if(enemy.isDead()){
            afterCombat();
        }
    }

    private void updateEnemyGUI() {
        if (enemy != null){
            enemyHealthBar.setProgress((double) enemy.getCurrentHealth() / enemy.getMaxHealth());
        }
        enemyName.setText(enemy.getName());
        enemyName.setVisible(true);
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
