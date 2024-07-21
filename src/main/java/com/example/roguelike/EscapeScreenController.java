package com.example.roguelike;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EscapeScreenController {
    @FXML
    private Button nextLevelButton;

    @FXML
    public void initialize(){
        nextLevelButton.setOnAction(event -> nextLevel());
    }

    private void nextLevel() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) nextLevelButton.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
