package com.example.roguelike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CombatScreenController {
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

    public void initialize(){
        attackButton.setOnAction(event -> attackEnemy());
        itemsButton.setOnAction(event -> showItems());
        runButton.setOnAction(event -> escapeEncounter());

        testButton.setOnAction(event -> afterCombat());
        endTestButton.setOnAction(event -> winGame());
        defeatTest.setOnAction(event -> defeated());
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

    private void attackEnemy() {
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
