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
    
    @FXML
    public void initialize(){
        startButton.setOnAction(event -> startGame());
        continueButton.setOnAction(event -> continueGame());
        catalogButton.setOnAction(event -> openCatalog());
        optionsButton.setOnAction(event -> showOptions());
        exitButton.setOnAction(event -> exitGame());
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
    }

    private void startGame() {
        //Start Game Code Here
        player = new Player("Player", 20, 1, 0);
        player.savePlayerData();

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
