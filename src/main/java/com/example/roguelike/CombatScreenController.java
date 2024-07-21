package com.example.roguelike;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class CombatScreenController {
    @FXML
    private Button attackButton;
    @FXML
    private Button itemsButton;
    @FXML
    private Button runButton;
    @FXML
    private Text levelIndicator;
    
    //inventory grid
    @FXML
    private GridPane inventoryGrid;
    @FXML
    private Button closeInventoryButton;
    @FXML
    private Rectangle gridBackground;
    private ItemManager itemManager = new ItemManager();
    
    //Test Buttons
    @FXML
    private Button testButton;
    @FXML
    private Button endTestButton;
    @FXML
    private Button defeatTest;
    @FXML
    private Button takeDamageButton;
    @FXML
    private Button testItemsButton;

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
    @FXML
    private Text playerHealthNumbers;

    private int numOfActions = 0;

    //Enemy elements
    @FXML //@FML haha lol 
    private ProgressBar enemyHealthBar;
    @FXML
    private Text enemyName;
    @FXML
    private Text enemyHitIndicator;
    @FXML
    private ImageView enemyImage;
    @FXML
    private Text enemyHealthNumbers;
    @FXML
    private Text cannotRunText;


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
        //inventory screen
        itemsButton.setOnAction(event -> showItems());
        inventoryGrid.setVisible(false);
        closeInventoryButton.setVisible(false);
        gridBackground.setVisible(false);
        closeInventoryButton.setOnAction(event -> hideItems());
        
        //Level Initialization
        level = new Level();
        levelIndicator.setText("Level " + level.getLevel());
        
        //Spawns the Enemy
        spawnEnemy();
        enemyImage.setImage(enemy.getImageForEnemy(enemy));
        enemyName.setVisible(true);
        enemyHealthNumbers.setText(enemy.getCurrentHealth() + " / " + enemy.getMaxHealth());
        level.setRewardMoney(enemy.getRewardMoney());

        //link the sprite of the enemy to the enemy that spawns


        enemyHitIndicator.setVisible(false);

        //TODO Conditional Statement to switch the enemy sprite based on the enemy that spawns
        //partially done, but needs to be completed for enemy bosses (as enemy bosses need to be overhauled / rethought / reworked / reimagined / re-everythinged)
        //no lie half of that was completed by intellisense, and everytime intellisense does something like this, i fall more and more in love with it


        attackButton.setOnAction(event -> handleAttack());
        runButton.setOnAction(event -> escapeEncounter());

        //Player Linking
        player = new Player("playerData.txt");
        playerHealthBar.setProgress(player.getCurrentHealth() / player.getMaxHealth());
        playerHitIndicator.setVisible(false);
        playerHealthNumbers.setText(player.getCurrentHealth() + " / " + player.getMaxHealth());


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

        //hideTestButtons();

        //Test Buttons
        testButton.setOnAction(event -> afterCombat());
        endTestButton.setOnAction(event -> winGame());
        defeatTest.setOnAction(event -> defeated());
        takeDamageButton.setOnAction(event -> player.takeDamage(5, enemy));
        testItemsButton.setOnAction(event -> debugDisplayItems());

    }

    private void hideTestButtons(){
        testButton.setVisible(false);
        endTestButton.setVisible(false);
        defeatTest.setVisible(false);
        takeDamageButton.setVisible(false);
        testItemsButton.setVisible(false);
    }

    private void updatePlayerGUI(){
        //Updates player health on the progress bar
        if (player != null){
            playerHealthBar.setProgress((double) player.getCurrentHealth() / player.getMaxHealth());
            playerHealthNumbers.setText(player.getCurrentHealth() + " / " + player.getMaxHealth());
        }
    }

    private void spawnEnemy(){
        //Checks the level and spawns the appropriate enemy
        int currentLevel = level.getLevel() % 10;
        switch (currentLevel){
            case 5:
                enemy = enemy.spawnRandomEnemy(EnemyType.STRONG);
                break;
            case 0:
                enemy = enemy.spawnRandomBoss();
                break;
            default:
                enemy = enemy.spawnRandomEnemy(EnemyType.REGULAR);
                break;
        }
        updateEnemyGUI();    
    }

    private void handleAttack(){
        //action is incremented
        numOfActions++;

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
        double yellowStart = (totalWidth - regularHitWidth) / 2;

        do{
            greenStart = rand.nextDouble() * (totalWidth - critWidth);
        } while (Math.abs(yellowStart - greenStart) < critWidth + regularHitWidth && yellowStart < greenStart + critWidth); 
        
        //Make sure that thezones stay within the bounds PLEASE PLEASE PLEASE STAY WITHIN THE BOUNDS I BEG YOU PLEASE
        greenStart = Math.max(0, Math.min(greenStart, totalWidth - critWidth));
        

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

        double barCenter = qteBar.getTranslateX() + qteBar.getWidth() / 2; //unused But just in case yk (aka im too lazy to delete this)
        double greenStart = greenZone.getTranslateX();
        double greenEnd = greenStart + greenZone.getWidth();

        double yellowStart = yellowZone.getTranslateX();
        double yellowEnd = yellowStart + yellowZone.getWidth();

        double attackMultiplier;

        if(barStart <= greenEnd && barEnd >= greenStart){
            //Crit Damage
            attackMultiplier = player.getCriticalDamageMultiplier(); 
            playerHitIndicator.setText("CRIT! Dealt " + (int) Math.ceil(player.getAttackPower() * attackMultiplier) + " damage!");

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

        //if the player has extra actions, then only after the second action will the enemy attack
        //if the player Doesnt have extra actions, then the enemy attacks regardless
        if(player.checkExtraActions()){
            if(numOfActions > 1){
                enemyAttack();
            }
        } else{
            enemyAttack();
        }
    }

    

    private void defeated() {
        level.setDead();
        level.saveLevelData();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DefeatedScreen.fxml"));
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
        //shows items in a grid after the player clicks the items button
        //set the visibility to true
        inventoryGrid.setVisible(true);
        closeInventoryButton.setVisible(true);
        gridBackground.setVisible(true);

        //set the other buttons to invisible
        attackButton.setVisible(false);
        itemsButton.setVisible(false);
        runButton.setVisible(false);

        inventoryGrid.getChildren().clear(); //clear the existing content
        int row = 0, column = 0;
        for(Item item : player.getInventory()){
            ImageView itemImage = new ImageView(itemManager.getImageForItem(item));
            itemImage.setFitHeight(50);
            itemImage.setFitWidth(50);
            inventoryGrid.add(itemImage, column, row);
            
            column++;

            if(column == 5){
                column = 0;
                row++;
            }
        }

    }

    private void hideItems(){
        //gets rid of the items menu after show items shows
        inventoryGrid.setVisible(false);
        closeInventoryButton.setVisible(false);
        gridBackground.setVisible(false);

        attackButton.setVisible(true);
        itemsButton.setVisible(true);
        runButton.setVisible(true);
    }

    private void escapeEncounter() {
        if(level.getLevel() % 5 != 0) { //only can escape if the level is not a boss level or a strong enemy level (5, 10, 15, etc)
            //TODO check if this works after boss enemies are implemented

            //reward money should be reset, and no items should be shown, but the player should be able to go to the next level
            level.setRewardMoney(0);
            level.levelup();
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EscapeScreen.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) attackButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            if(level.getLevel() % 10 == 5){
                //if the level is a strong enemy level, then the player cannot run
                cannotRunText.setText("Cannot Run from Strong Enemy");
            } else{
                //if the level is a boss level, then the player cannot run
                cannotRunText.setText("Cannot Run from Bosses");
            }
            cannotRunText.setVisible(true);

        }   
    }

    private void enemyAttack() {
        String enemyAction = enemy.enemyTurn();
        enemyHitUpdate(enemyAction);
        //animate the enemy attacking also maybe???? (depends on if the player gets animated)

        updatePlayerGUI();
        
        if(player.checkPlayerDead()){
            defeated();
        }
    }

    private void enemyHitUpdate(String action) {
        switch (action.toLowerCase()){
            case "attack":
                enemyHitIndicator.setText("Enemy Attacked for " + enemy.getAttackPower());
                enemyHitIndicator.setVisible(true);
                break;
            case "block":
                enemyHitIndicator.setText("Enemy Blocked");
                enemyHitIndicator.setVisible(true);
                break;
        }
    }

    private void attackEnemy(double attackMultiplier) {
        //potential TODO animate the player attacking maybe? 

        //check player item stats
        if(player.isDoubleAttack()){
            //literally a second attack so im just gonna copy paste whatever is below
            player.attack(enemy, attackMultiplier);
            if(player.getHealPerTurn() > 0){
                player.heal(player.getHealPerTurn());
            }
        }

        player.attack(enemy, attackMultiplier);
        if(player.getHealPerTurn() > 0){
            player.heal(player.getHealPerTurn());
        }

        updateEnemyGUI();

        if(enemy.isDead()){
            afterCombat();
        }
    }

    private void updateEnemyGUI() {
        if (enemy != null){
            enemyHealthBar.setProgress((double) enemy.getCurrentHealth() / enemy.getMaxHealth());
            enemyHealthNumbers.setText(enemy.getCurrentHealth() + " / " + enemy.getMaxHealth());
        }
        enemyName.setText(enemy.getName());
        enemyName.setVisible(true);
    }

    public void afterCombat(){
        player.savePlayerData(); //save data before leaving

        if(level.getLevel() != 20){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RewardsScreen.fxml"));
                Scene scene = new Scene(loader.load());
                Stage stage = (Stage) attackButton.getScene().getWindow();
                stage.setScene(scene);

                //Increment level by 1
                level.levelup();

                Platform.runLater(() -> levelIndicator.setText("Level " + level.getLevel()));
            } catch (IOException e){
                e.printStackTrace();
            }
        } else{
            winGame();
        }
    }

    private void debugDisplayItems() {
        for(Item item : player.getInventory()){
            System.out.println(item.getName());
        }
    }
}
