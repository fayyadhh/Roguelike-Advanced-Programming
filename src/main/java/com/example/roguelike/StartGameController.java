package com.example.roguelike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGameController {
    @FXML
    private Button enterDungeonButton, backToMenuButton;
    @FXML
    private ImageView keepsake1;
    @FXML
    private ImageView keepsake2;
    @FXML
    private ImageView keepsake3;
    @FXML
    private ImageView keepsake4;
    @FXML
    private ImageView keepsake5;

    @FXML
    public void initialize(){
        //update keepsake images

        //initialize buttons
        enterDungeonButton.setOnAction(event -> enterDungeon());
        backToMenuButton.setOnAction(event -> backToMenu());

    }

    private void enterDungeon() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) enterDungeonButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void backToMenu() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) backToMenuButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
