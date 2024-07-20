package com.example.roguelike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button startButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button catalogButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button exitButton;

    Player player;
    Level level = new Level();
    
    @FXML
    public void initialize(){
        startButton.setOnAction(event -> startGame());
        catalogButton.setOnAction(event -> openCatalog());
        optionsButton.setOnAction(event -> showOptions());
        exitButton.setOnAction(event -> exitGame());

        
        //continue button logic is here
        if(!(level.checkIfDead() && (level.getLevel() > 1))){ //if not dead and its not level 1
            //change the opacity first
            continueButton.setOpacity(1);
            continueButton.setDisable(false);
            continueButton.setOnAction(event -> continueGame());
            
        } else {
            //continue button should be less opaque if there is no save data
            continueButton.setOpacity(0.5);
            continueButton.setDisable(true);
        }
    }

    private void exitGame() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void showOptions() {
        //Options Menu Code Here
    }

    private void openCatalog() {
        //Catalog Code Here
    }

    private void continueGame() {
        //Continue Game Code Here
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startGame() {
        //Start Game Code Here
        player = new Player();
        player.savePlayerData();

        level.resetForNewGame();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartGameScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
