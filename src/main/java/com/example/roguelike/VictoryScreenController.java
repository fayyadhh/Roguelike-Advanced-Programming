package com.example.roguelike;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VictoryScreenController {
    @FXML
    private Button continueButton;
    @FXML
    private Button mainMenuButton;

    @FXML
    public void initialize() {
        continueButton.setOnAction(e -> {
            continueButton.setOnAction(event -> returnToCombatScreen());
        });

        mainMenuButton.setOnAction(e -> {
            mainMenuButton.setOnAction(event -> returnToMainMenu());
        });
    }

    private void returnToCombatScreen() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void returnToMainMenu() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) mainMenuButton.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
